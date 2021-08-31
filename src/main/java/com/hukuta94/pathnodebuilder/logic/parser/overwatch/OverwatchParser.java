package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO The first bad code prototype of input data parser. It needs to be refactored in future
public class OverwatchParser
{
    private static final Pattern PATTERN_VARIABLES = Pattern.compile(
            "(?<globalVar>global:)|(?<nodePositions>\\d{1,2}\\s*:\\s*.BuilderNodePositions$)|(?<nodeConnections>\\d{1,2}\\s*:\\s*.BuilderNodeConnections$)",
            Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern PATTERN_NODE_POSITIONS = Pattern.compile(
            "(?: ?\\G|^\\s*Global\\.BuilderNodePositions\\s*=\\s*)(?:Array\\()(?<array>[aA-zZ,\\(\\-0-9\\.\\s\\)]+)",
            Pattern.MULTILINE);

    private static final Pattern PATTERN_NODE_CONNECTIONS = Pattern.compile(
            "(?: ?\\G|^\\s*Global\\.BuilderNodeConnections\\s*=\\s*)(?:Array\\()(?<array>[aA-zZ,\\(\\-0-9\\.\\s\\)]+)",
            Pattern.MULTILINE);

    private static final Pattern PATTERN_SPLIT_VECTOR_TO_COORDS = Pattern.compile(
            "(?<x>\\-?\\d+\\.?\\d+)\\s*\\,\\s*(?<y>\\-?\\d+\\.?\\d+)\\s*\\,\\s*(?<z>\\-?\\d+\\.?\\d+)");

    private static final String REGEX_SPLIT_ARRAY_CONNECTION_TO_PARTS = "[Aarray\\(\\,\\s\\)]+";

    private static final String REGEX_SPLIT_ARRAY_TO_PARTS = "\\,\\s*(?=F|A|V)";

    public RuleActionCodeSnippet parseInputData(String inputString)
    {
        Variables variables = parseVariablesBlock(inputString);
        Actions actions = new Actions();

        parseNodePositions(inputString, actions);
        parseNodeConnections(inputString, actions);
        return new RuleActionCodeSnippet(variables, actions);
    }

    private Variables parseVariablesBlock(String inputString)
    {
        Variables variables = new Variables();

        Matcher matcher = PATTERN_VARIABLES.matcher(inputString);
        if (matcher.find())
        {
            if (matcher.group("globalVar").equals("global:"))
            {
                matcher.find();
                String globalVar = matcher.group("nodePositions");
                if (globalVar != null)
                {
                    String[] parts = globalVar.split("\\s*:\\s*");
                    Integer index = Integer.parseInt(parts[0]);
                    variables.addGlobal(index, parts[1]);
                }

                matcher.find();
                globalVar = matcher.group("nodeConnections");
                if (globalVar != null)
                {
                    String[] parts = globalVar.split("\\s*:\\s*");
                    Integer index = Integer.parseInt(parts[0]);
                    variables.addGlobal(index, parts[1]);
                }
            }
        }

        return variables;
    }

    private void parseNodePositions(String inputString, Actions actions)
    {
        Matcher matcher = PATTERN_NODE_POSITIONS.matcher(inputString);

        while (matcher.find())
        {
            Array<Vector> nodePositionsArray = new Array<>();
            String[] vectors = matcher.group("array").split(REGEX_SPLIT_ARRAY_TO_PARTS);

            for (String item : vectors)
            {
                if (item.toLowerCase().contains("vector"))
                {
                    Matcher coordMatcher = PATTERN_SPLIT_VECTOR_TO_COORDS.matcher(item);
                    if (coordMatcher.find()) {

                        nodePositionsArray.add(
                            new Vector(
                                Double.parseDouble(coordMatcher.group("x")),
                                Double.parseDouble(coordMatcher.group("y")),
                                Double.parseDouble(coordMatcher.group("z"))
                            )
                        );
                    }
                }
            }

            Variable<Array> variable = new Variable<>("BuilderNodePositions", nodePositionsArray);
            actions.add(variable);
        }
    }

    private void parseNodeConnections(String inputString, Actions actions)
    {
        Matcher matcher = PATTERN_NODE_CONNECTIONS.matcher(inputString);

        while (matcher.find())
        {
            Array<Array> nodeConnectionsArray = new Array<>();
            String[] arrays = matcher.group("array").split(REGEX_SPLIT_ARRAY_TO_PARTS);

            for (String item : arrays)
            {
                if (item.toLowerCase().contains("array"))
                {
                    String[] arrayParts = item.split(REGEX_SPLIT_ARRAY_CONNECTION_TO_PARTS);
                    if (arrayParts.length == 0) {
                        continue;
                    }

                    Array<Integer> connections = new Array<>();
                    for (String part : arrayParts) {
                        if (!part.isEmpty()) {
                            connections.add(Integer.parseInt(part));
                        }
                    }

                    nodeConnectionsArray.add(connections);
                }
            }

            Variable<Array> variable = new Variable<>("BuilderNodeConnections", nodeConnectionsArray);
            actions.add(variable);
        }
    }
}
