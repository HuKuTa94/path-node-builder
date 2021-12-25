package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.common.types.ParsedInputData;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO It has bad code and it needs to be refactored
@Service
public class OverwatchParser
{
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

    private static final Pattern PATTERN_SPLIT_VECTOR_TO_COORDS = Pattern.compile(
            "(?<x>\\-?\\d+(\\.\\d+)?)\\s*\\,\\s*(?<y>\\-?\\d+(\\.?\\d+)?)\\s*\\,\\s*(?<z>\\-?\\d+(\\.?\\d+)?)");

    private static final String REGEX_CAPTURE_ARRAY_WITH_ITEMS = "(?: ?\\G|^\\s*Global\\.%s\\s*=\\s*)(?:Array\\()(?<%s>[aA-zZ,\\(\\-0-9\\.\\s\\)]+)";
    private static final String REGEX_EXTRACT_ITEMS_FROM_ARRAY = "\\,\\s*(?=F|A|V)";
    private static final String REGEX_SPLIT_ITEM_TO_PARTS = "[^0-9]+";

    // Regex capture group names
    private static final String GROUP_ARRAY = "array";

    private final Validator validator;

    @Autowired
    public OverwatchParser(
            Validator validator,
            @Value("${overwatch.variables.input.positions-name}") String inputVarPositionsName,
            @Value("${overwatch.variables.input.connections-name}") String inputVarConnectionsName,
            @Value("${overwatch.variables.output.positions-name}") String outputVarPositionsName,
            @Value("${overwatch.variables.output.positions-index}") Integer outputVarPositionsIndex,
            @Value("${overwatch.variables.output.connections-name}") String outputVarConnectionsName,
            @Value("${overwatch.variables.output.connections-index}") Integer outputVarConnectionsIndex,
            @Value("${overwatch.variables.output.matrix-name}") String outputVarMatrixName,
            @Value("${overwatch.variables.output.matrix-index}") Integer outputVarMatrixIndex)
    {
        this.validator = validator;

        // Init variable names from config
        INPUT_VAR_POSITIONS_NAME = inputVarPositionsName;
        INPUT_VAR_CONNECTIONS_NAME = inputVarConnectionsName;
        OUTPUT_VAR_POSITIONS_NAME = outputVarPositionsName;
        OUTPUT_VAR_POSITIONS_INDEX = outputVarPositionsIndex;
        OUTPUT_VAR_CONNECTIONS_NAME = outputVarConnectionsName;
        OUTPUT_VAR_CONNECTIONS_INDEX = outputVarConnectionsIndex;
        OUTPUT_VAR_MATRIX_NAME = outputVarMatrixName;
        OUTPUT_VAR_MATRIX_INDEX = outputVarMatrixIndex;
    }

    public ParsedInputData parseInputData(String inputString) throws Exception
    {
        // Try to find input vars in string
        validator.validateInputString(inputString);

        Vector[] inputNodePositions = parseNodePositions(inputString);
        int[][] inputNodeConnections = parseNodeConnections(inputString);

        return new ParsedInputData(inputNodePositions, inputNodeConnections);
    }

    public String parseOutputData(
            Vector[] outputPositions,
            int[][] outputConnections,
            Map<Integer, List<List<Integer>>> distanceMatrix)
    {
        StringBuilder builder = new StringBuilder();
        // Variables block
        builder.append("variables\n{\n\tglobal:\n\t\t");
        builder.append(OUTPUT_VAR_POSITIONS_INDEX);
        builder.append(": ");
        builder.append(OUTPUT_VAR_POSITIONS_NAME);
        builder.append("\n\t\t");

        builder.append(OUTPUT_VAR_CONNECTIONS_INDEX);
        builder.append(": ");
        builder.append(OUTPUT_VAR_CONNECTIONS_NAME);
        builder.append("\n\t\t");

        builder.append(OUTPUT_VAR_MATRIX_INDEX);
        builder.append(": ");
        builder.append(OUTPUT_VAR_MATRIX_NAME);

        // Actions block
        builder.append("\n}\nactions\n{\n");

        // Node positions
        builder.append("\tGlobal.");
        builder.append(OUTPUT_VAR_POSITIONS_NAME);
        builder.append(" =\n");
        convertNodePositions(builder, outputPositions);
        builder.append("\n");

        // Node connections
        builder.append("\tGlobal.");
        builder.append(OUTPUT_VAR_CONNECTIONS_NAME);
        builder.append(" =\n");
        convert2DArray(builder, outputConnections);
        builder.append("\n");

        // Distance matrix
        builder.append("\tGlobal.");
        builder.append(OUTPUT_VAR_MATRIX_NAME);
        builder.append(" =\n");
        convertDistanceMatrix(builder, distanceMatrix);
        builder.append("\n}\n");

        return builder.toString();
    }

    private void convertNodePositions(StringBuilder builder, Vector[] elements)
    {
        int elementsSize = elements.length;

        if (elementsSize == 0) {
            builder.append("\t\tArray();");
            return;
        }

        // Begin fill array
        builder.append("\t\tArray(\n");

        for (int i = 0; i < elementsSize; i++)
        {
            builder.append("\t\t\tVector(");
            builder.append(elements[i].getX());
            builder.append(", ");
            builder.append(elements[i].getY());
            builder.append(", ");
            builder.append(elements[i].getZ());
            builder.append(")");

            // Dont put ", " if it is the last element
            if (i != elementsSize - 1) {
                builder.append(",\n");
            }
        }

        builder.append(");");
    }

    private void convert2DArray(StringBuilder builder, int[][] elements)
    {
        int elementsSize = elements.length;

        if (elementsSize == 0) {
            builder.append("\t\tArray();");
            return;
        }

        // Begin fill array
        builder.append("\t\tArray(\n");

        for (int i = 0; i < elementsSize; i++)
        {
            builder.append("\t\t\tArray(");
            for (int j = 0; j < elements[i].length; j++)
            {
                builder.append(elements[i][j]);
                if (j != elements[i].length - 1) {
                    builder.append(", ");
                } else {
                    builder.append(")");
                }
            }

            // Dont put ", " if it is the last element
            if (i != elementsSize - 1) {
                builder.append(",\n");
            }
        }

        builder.append(");");
    }

    private void convertDistanceMatrix(StringBuilder builder, Map<Integer, List<List<Integer>>> distanceMatrix)
    {
        int matrixSize = distanceMatrix.size();

        if (matrixSize == 0) {
            builder.append("\t\tArray();");
            return;
        }

        // Begin fill array
        builder.append("\t\tArray(\n");

        for (int i = 0; i < matrixSize; i++)
        {
            builder.append("\t\t\tArray(");
            for (int j = 0; j < distanceMatrix.get(i).size(); j++)
            {
                // Inner array contains two values
                // Uni-direction node
                if (distanceMatrix.get(i).get(j).size() > 1)
                {
                    convertArray(builder, distanceMatrix.get(i).get(j));
                    if (j != distanceMatrix.get(i).size() - 1) {
                        builder.append(", ");
                    } else {
                        builder.append(")");
                    }
                    continue;
                }

                builder.append(distanceMatrix.get(i).get(j).get(0));
                if (j != distanceMatrix.get(i).size() - 1) {
                    builder.append(", ");
                } else {
                    builder.append(")");
                }
            }

            // Dont put ", " if it is the last element
            if (i != matrixSize - 1) {
                builder.append(",\n");
            }
        }

        builder.append(");");
    }

    private void convertArray(StringBuilder builder, List<Integer> array)
    {
        builder.append("Array(");
        for (int i = 0; i < array.size(); i++)
        {
            builder.append(array.get(i));
            if (i != array.size() - 1) {
                builder.append(", ");
            } else {
                builder.append(")");
            }
        }
    }

    private Vector[] parseNodePositions(String inputString) throws Exception {
        // Extract from var the array with items
        Pattern pattern = Pattern.compile(
                String.format(REGEX_CAPTURE_ARRAY_WITH_ITEMS, INPUT_VAR_POSITIONS_NAME, GROUP_ARRAY),
                Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(inputString);

        // Try find input var in string
        if (matcher.find())
        {
            String[] items = matcher.group(GROUP_ARRAY).split(REGEX_EXTRACT_ITEMS_FROM_ARRAY);
            Vector[] resultArray = new Vector[items.length];

            for (int i = 0; i < items.length; i++)
            {
                String item = items[i];

                // Item is empty (it was deleted in Path Node Builder - Overwatch Workshop mod)
                if (item.toLowerCase().contains("false"))
                {
                    // Don't skip these elements because we need to optimize data before computing the distance matrix:
                    // if we delete the path node, all indexes in connection arrays must be recomputed
                    resultArray[i] = null;
                    continue;
                }

                Matcher vectorMatcher = PATTERN_SPLIT_VECTOR_TO_COORDS.matcher(item);
                if (vectorMatcher.find())
                {
                    resultArray[i] = new Vector(
                            Double.parseDouble(vectorMatcher.group("x")),
                            Double.parseDouble(vectorMatcher.group("y")),
                            Double.parseDouble(vectorMatcher.group("z")));
                }
            }

            if (resultArray.length == 1)
            {
                throw new Exception("Variable 'Global.".concat(INPUT_VAR_POSITIONS_NAME).concat("' contains incorrect data"));
            }

            return resultArray;
        }

        throw new Exception("Variable 'Global.".concat(INPUT_VAR_POSITIONS_NAME).concat("' not found or does not contain value"));
    }

    private int[][] parseNodeConnections(String inputString) throws Exception {
        // Extract from var the array with items
        Pattern pattern = Pattern.compile(
                String.format(REGEX_CAPTURE_ARRAY_WITH_ITEMS, INPUT_VAR_CONNECTIONS_NAME, GROUP_ARRAY),
                Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(inputString);

        // Try find input var in string
        if (matcher.find())
        {
            String[] items = matcher.group(GROUP_ARRAY).split(REGEX_EXTRACT_ITEMS_FROM_ARRAY);
            int[][] resultArray = new int[items.length][];

            for (int i = 0; i < items.length; i++)
            {
                String item = items[i];

                // Item is empty (it was deleted in Path Node Builder - Overwatch Workshop mod)
                if (item.toLowerCase().contains("false"))
                {
                    // Don't skip these elements because we need to optimize data before computing the distance matrix:
                    // if we delete the path node, all indexes in connection arrays must be recomputed
                    resultArray[i] = null;
                    continue;
                }

                //TODO Супер колхоз, надо переписать это
                String[] itemParts = item.split(REGEX_SPLIT_ITEM_TO_PARTS);

                if (itemParts.length == 0) {
                    resultArray[i] = null;
                    continue;
                }

                int[] connections = new int[itemParts.length - 1];
                for (int j = 0; j < itemParts.length; j++) {
                    String part = itemParts[j];
                    if (!part.isEmpty()) {
                        connections[j-1] = Integer.parseInt(part);
                    }
                }

                resultArray[i] = connections;
            }

            if (resultArray.length == 1)
            {
                throw new Exception("Variable 'Global.".concat(INPUT_VAR_CONNECTIONS_NAME).concat("' contains incorrect data"));
            }

            return resultArray;
        }

        throw new Exception("Variable 'Global.".concat(INPUT_VAR_CONNECTIONS_NAME).concat("' not found or does not contain value"));
    }
}
