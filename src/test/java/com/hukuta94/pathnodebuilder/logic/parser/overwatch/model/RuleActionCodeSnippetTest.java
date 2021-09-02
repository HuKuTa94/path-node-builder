package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleActionCodeSnippetTest
{
    @Test
    @DisplayName("Basic result of code snippet of actions for the rule")
    void basicCodeSnippetOfActionsForRuleTest()
    {
        // Given
        String expectedResult =
            "variables\n" +
            "{\n" +
            "\tglobal:\n" +
            "\t\t17: BuilderNodePositions\n" +
            "}\n" +
            "\n" +
            "actions\n" +
            "{\n" +
            "\tGlobal.BuilderNodePositions = \n" +
            "\t\tArray(\n" +
            "\t\t\tVector(-16.004, 0.35, -15.965), \n" +
            "\t\t\tVector(-15.994, 0.35, -0.043), \n" +
            "\t\t\tVector(-15.981, 0.35, 15.908));\n" +
            "}";

        // Variable type declaration
        Variable<Array<Vector>> builderNodePositionsVar = new Variable<>(17, "BuilderNodePositions");

        // Variable value definition
        Array<Vector> builderNodePositionsVarValue = new Array<>(3);
        builderNodePositionsVarValue.add(new Vector(-16.004, 0.35, -15.965));
        builderNodePositionsVarValue.add(new Vector(-15.994, 0.35, -0.043));
        builderNodePositionsVarValue.add(new Vector(-15.981, 0.35, 15.908));
        builderNodePositionsVar.setValue(builderNodePositionsVarValue);

        // Variables block
        VariableBlock variableBlock = new VariableBlock();
        variableBlock.addGlobal(builderNodePositionsVar);

        // Actions block
        ActionBlock actionBlock = new ActionBlock();
        actionBlock.add(builderNodePositionsVar);

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(variableBlock, actionBlock);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Inner array test")
    void innerArrayTest()
    {
        // Given
        String expectedResult =
            "variables\n" +
            "{\n" +
            "\tglobal:\n" +
            "\t\t18: BuilderNodeConnections\n" +
            "}\n" +
            "\n" +
            "actions\n" +
            "{\n" +
            "\tGlobal.BuilderNodeConnections = \n" +
            "\t\tArray(\n" +
            "\t\t\tArray(5, 1, 6), \n" +
            "\t\t\tArray(0, 2), \n" +
            "\t\t\tArray(3, 1), \n" +
            "\t\t\tArray(2, 6, 4, 1, 5));\n" +
            "}";

        // Variable type declaration
        Variable<Array<Array<Integer>>> BuilderNodeConnectionsVar = new Variable<>(18, "BuilderNodeConnections");

        // Variable value definition
        Array<Array<Integer>> BuilderNodeConnectionsVarValue = new Array<>(3);
        BuilderNodeConnectionsVarValue.add(new Array<>(5, 1, 6));
        BuilderNodeConnectionsVarValue.add(new Array<>(0, 2));
        BuilderNodeConnectionsVarValue.add(new Array<>(3, 1));
        BuilderNodeConnectionsVarValue.add(new Array<>(2, 6, 4, 1, 5));
        BuilderNodeConnectionsVar.setValue(BuilderNodeConnectionsVarValue);

        // Variables block
        VariableBlock variableBlock = new VariableBlock();
        variableBlock.addGlobal(BuilderNodeConnectionsVar);

        // Actions block
        ActionBlock actionBlock = new ActionBlock();
        actionBlock.add(BuilderNodeConnectionsVar);

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(variableBlock, actionBlock);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Array 1D and array 2D")
    void bothArrayTypesTest()
    {
        // Given
        String expectedResult =
            "variables\n" +
            "{\n" +
            "\tglobal:\n" +
            "\t\t17: BuilderNodePositions\n" +
            "\t\t18: BuilderNodeConnections\n" +
            "}\n" +
            "\n" +
            "actions\n" +
            "{\n" +
            "\tGlobal.BuilderNodePositions = \n" +
            "\t\tArray(\n" +
            "\t\t\tVector(-15.994, 0.35, -0.043), \n" +
            "\t\t\tVector(-15.981, 0.35, 15.908));\n" +
            "\tGlobal.BuilderNodeConnections = \n" +
            "\t\tArray(\n" +
            "\t\t\tArray(3, 1), \n" +
            "\t\t\tArray(2, 6, 4, 1, 5));\n" +
            "}";

        // Variable type declaration
        Variable<Array<Vector>> builderNodePositionsVar = new Variable<>(17, "BuilderNodePositions");
        Variable<Array<Array<Integer>>> BuilderNodeConnectionsVar = new Variable<>(18, "BuilderNodeConnections");

        // Variable value definition
        Array<Vector> builderNodePositionsVarValue = new Array<>(3);
        builderNodePositionsVarValue.add(new Vector(-15.994, 0.35, -0.043));
        builderNodePositionsVarValue.add(new Vector(-15.981, 0.35, 15.908));
        builderNodePositionsVar.setValue(builderNodePositionsVarValue);

        Array<Array<Integer>> BuilderNodeConnectionsVarValue = new Array<>(3);
        BuilderNodeConnectionsVarValue.add(new Array<>(3, 1));
        BuilderNodeConnectionsVarValue.add(new Array<>(2, 6, 4, 1, 5));
        BuilderNodeConnectionsVar.setValue(BuilderNodeConnectionsVarValue);

        // Variables block
        VariableBlock variableBlock = new VariableBlock();
        variableBlock.addGlobal(builderNodePositionsVar);
        variableBlock.addGlobal(BuilderNodeConnectionsVar);

        // Actions block
        ActionBlock actionBlock = new ActionBlock();
        actionBlock.add(builderNodePositionsVar);
        actionBlock.add(BuilderNodeConnectionsVar);

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(variableBlock, actionBlock);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        assertEquals(expectedResult, actualResult);
    }
}
