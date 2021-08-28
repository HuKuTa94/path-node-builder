package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

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

        // Variables block
        Variables vars = new Variables();
        vars.addGlobal(17, "BuilderNodePositions");

        // Actions block
        Array<Vector> array = new Array<>(3);
        array.add(new Vector(-16.004, 0.35, -15.965));
        array.add(new Vector(-15.994, 0.35, -0.043));
        array.add(new Vector(-15.981, 0.35, 15.908));

        Actions actions = new Actions();
        actions.add(new Variable<>("BuilderNodePositions", array));

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(vars, actions);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
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

        // Variables block
        Variables vars = new Variables();
        vars.addGlobal(18, "BuilderNodeConnections");

        // Actions block
        Array<Array> array = new Array<>(3);

        Array<Integer> innerArray;

        innerArray = new Array<>(3);
        innerArray.addAll(5, 1, 6);
        array.add(innerArray);

        innerArray = new Array<>(2);
        innerArray.addAll(0, 2);
        array.add(innerArray);

        innerArray = new Array<>(2);
        innerArray.addAll(3, 1);
        array.add(innerArray);

        innerArray = new Array<>(5);
        innerArray.addAll(2, 6, 4, 1, 5);
        array.add(innerArray);

        Actions actions = new Actions();
        actions.add(new Variable<>("BuilderNodeConnections", array));

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(vars, actions);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
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

        // Variables block
        Variables vars = new Variables();
        vars.addGlobal(17, "BuilderNodePositions");
        vars.addGlobal(18, "BuilderNodeConnections");

        // Actions block
        Array<Vector> array1D = new Array<>(1);
        array1D.add(new Vector(-15.994, 0.35, -0.043));
        array1D.add(new Vector(-15.981, 0.35, 15.908));

        Array<Array> array2D = new Array<>(2);
        Array<Integer> innerArray;
        innerArray = new Array<>(2);
        innerArray.addAll(3, 1);
        array2D.add(innerArray);

        innerArray = new Array<>(5);
        innerArray.addAll(2, 6, 4, 1, 5);
        array2D.add(innerArray);

        Actions actions = new Actions();
        actions.add(new Variable<>("BuilderNodePositions", array1D));
        actions.add(new Variable<>("BuilderNodeConnections", array2D));

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet codeSnippet = new RuleActionCodeSnippet(vars, actions);

        // When
        String actualResult = codeSnippet.toString();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
