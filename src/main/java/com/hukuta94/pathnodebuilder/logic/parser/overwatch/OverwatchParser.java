package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Function;

@Component
public class OverwatchParser
{
    private static boolean isInited = false;

    // Input variable names
    private static String INPUT_VAR_POSITIONS_NAME;
    private static String INPUT_VAR_CONNECTIONS_NAME;

    // Output variable names
    private static String OUTPUT_VAR_POSITIONS_NAME;
    private static String OUTPUT_VAR_CONNECTIONS_NAME;
    private static String OUTPUT_VAR_MATRIX_NAME;
    private static Integer OUTPUT_VAR_POSITIONS_INDEX;
    private static Integer OUTPUT_VAR_CONNECTIONS_INDEX;
    private static Integer OUTPUT_VAR_MATRIX_INDEX;

    // Patterns and regex to extract input data
    private static Pattern PATTERN_INPUT_VARS_EXIST;
    private static Pattern PATTERN_VAR_POSITIONS_ARRAY;
    private static Pattern PATTERN_VAR_CONNECTIONS_ARRAY;

    private static final Pattern PATTERN_SPLIT_VECTOR_TO_COORDS = Pattern.compile(
            "(?<x>\\-?\\d+\\.?\\d+)\\s*\\,\\s*(?<y>\\-?\\d+\\.?\\d+)\\s*\\,\\s*(?<z>\\-?\\d+\\.?\\d+)");

    private static final String REGEX_CAPTURE_ARRAY_WITH_ITEMS = "(?: ?\\G|^\\s*Global\\.%s\\s*=\\s*)(?:Array\\()(?<%s>[aA-zZ,\\(\\-0-9\\.\\s\\)]+)";
    private static final String REGEX_EXTRACT_ITEMS_FROM_ARRAY = "\\,\\s*(?=F|A|V)";
    private static final String REGEX_SPLIT_ITEM_TO_PARTS = "[Aarray\\(\\,\\s\\)]+";

    // Regex capture group names
    private static final String GROUP_POSITIONS = "positions";
    private static final String GROUP_CONNECTIONS = "connections";
    private static final String GROUP_ARRAY = "array";
    private static final String GROUP_VARS = "vars";

    @Autowired
    public OverwatchParser(
            @Value("${overwatch.variables.input.positions-name}") String inputVarPositionsName,
            @Value("${overwatch.variables.input.connections-name}") String inputVarConnectionsName,
            @Value("${overwatch.variables.output.positions-name}") String outputVarPositionsName,
            @Value("${overwatch.variables.output.positions-index}") Integer outputVarPositionsIndex,
            @Value("${overwatch.variables.output.connections-name}") String outputVarConnectionsName,
            @Value("${overwatch.variables.output.connections-index}") Integer outputVarConnectionsIndex,
            @Value("${overwatch.variables.output.matrix-name}") String outputVarMatrixName,
            @Value("${overwatch.variables.output.matrix-index}") Integer outputVarMatrixIndex)
    {
        if (isInited) {
            return;
        }

        isInited = true;

        // Init variable names from config
        INPUT_VAR_POSITIONS_NAME = inputVarPositionsName;
        INPUT_VAR_CONNECTIONS_NAME = inputVarConnectionsName;
        OUTPUT_VAR_POSITIONS_NAME = outputVarPositionsName;
        OUTPUT_VAR_POSITIONS_INDEX = outputVarPositionsIndex;
        OUTPUT_VAR_CONNECTIONS_NAME = outputVarConnectionsName;
        OUTPUT_VAR_CONNECTIONS_INDEX = outputVarConnectionsIndex;
        OUTPUT_VAR_MATRIX_NAME = outputVarMatrixName;
        OUTPUT_VAR_MATRIX_INDEX = outputVarMatrixIndex;

        // Init patterns
        PATTERN_INPUT_VARS_EXIST = Pattern.compile(
            String.format("(?<%s>global:)|(?<%s>\\d{1,2}\\s*:\\s*%s$)|(?<%s>\\d{1,2}\\s*:\\s*%s$)",
                    GROUP_VARS,
                    GROUP_POSITIONS,
                    INPUT_VAR_POSITIONS_NAME,
                    GROUP_CONNECTIONS,
                    INPUT_VAR_CONNECTIONS_NAME),
            Pattern.MULTILINE | Pattern.DOTALL);
    }

    public RuleActionCodeSnippet parseInputData(String inputString) throws Exception
    {
        Variables variables = parseVariablesBlock(inputString);
        Actions actions = new Actions();

        parseInputVarArray(
            inputString,
            INPUT_VAR_POSITIONS_NAME,
            16,
            actions,
            this::nodePositionItemProcessor
        );

        parseInputVarArray(
            inputString,
            INPUT_VAR_CONNECTIONS_NAME,
            17,
            actions,
            this::nodeConnectionItemProcessor
        );

        return new RuleActionCodeSnippet(variables, actions);
    }

    private Variables parseVariablesBlock(String inputString) throws Exception
    {
        // Try find input vars in string
        Matcher matcher = PATTERN_INPUT_VARS_EXIST.matcher(inputString);
        if (!matcher.find())
        {
            throw new Exception("Input string does not contain input data variables '" +
                INPUT_VAR_POSITIONS_NAME + "' and/or '" + INPUT_VAR_CONNECTIONS_NAME + "'");
        }

        Variables variables = new Variables();
        Actions actions = new Actions();

        // Input vars exist in the global block
        if (matcher.group(GROUP_VARS).equals("global:"))
        {
            // Vars exist, trying extract var indexes and names
            while (matcher.find())
            {
                String var = matcher.group();

                // This string has format "X: VarName", X - number in range from 0 to 127
                String[] varParts = var.split("\\s*\\:\\s*");
                variables.addGlobal(Integer.parseInt(varParts[0]), varParts[1]);
            }
        }

        return variables;
    }

    private <T> void parseInputVarArray(
            String inputString,
            String inputVarName,
            Integer inputVarIndex,
            Actions actions,
            Function<String, T> arrayItemProcessor)
    {
        // Extract from var the array with items
        Pattern pattern = Pattern.compile(
                String.format(REGEX_CAPTURE_ARRAY_WITH_ITEMS, inputVarName, GROUP_ARRAY),
                Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(inputString);

        // Try find input var in string
        while (matcher.find())
        {
            Array<T> array = new Array<>();
            String[] items = matcher.group(GROUP_ARRAY).split(REGEX_EXTRACT_ITEMS_FROM_ARRAY);

            for (String item : items)
            {
                // Item is empty (it was deleted in Path Node Builder - Overwatch Workshop mod)
                if (item.toLowerCase().contains("false"))
                {
                    // Don't skip these elements because we need to optimize data before computing the distance matrix:
                    // if we delete the path node, all indexes in connection arrays must be recomputed
                    array.add(null);
                    continue;
                }

                T processedItem = arrayItemProcessor.apply(item);

                // Don't put in the result array if the item is null.
                // Nullable items are detected above by keyword "false"
                if (processedItem != null) {
                    array.add(processedItem);
                }
            }

            Variable<Array> variable = new Variable<>(inputVarIndex, inputVarName, array);
            actions.add(variable);
        }
    }

    private Vector nodePositionItemProcessor(String item)
    {
        Matcher matcher = PATTERN_SPLIT_VECTOR_TO_COORDS.matcher(item);
        if (matcher.find())
        {
            return new Vector(
                Double.parseDouble(matcher.group("x")),
                Double.parseDouble(matcher.group("y")),
                Double.parseDouble(matcher.group("z")));
        }

        return null;
    }

    private Array<Integer> nodeConnectionItemProcessor(String item)
    {
        String[] itemParts = item.split(REGEX_SPLIT_ITEM_TO_PARTS);
        if (itemParts.length == 0) {
            return null;
        }

        Array<Integer> connections = new Array<>();
        for (String part : itemParts) {
            if (!part.isEmpty()) {
                connections.add(Integer.parseInt(part));
            }
        }

        return connections;
    }
}
