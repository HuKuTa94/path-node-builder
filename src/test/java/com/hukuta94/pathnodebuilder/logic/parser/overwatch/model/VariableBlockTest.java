package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VariableBlockTest
{
    @Test
    @DisplayName("Full code snippet of global and player vars block")
    void fullCodeSnippetForVariablesBlockTest()
    {
        // Given
        String expectedResult =
            "variables\n" +
            "{\n" +
            "\tglobal:\n" +
            "\t\t125: NodePositions\n" +
            "\t\t126: NodeConnections\n" +
            "\t\t127: DistanceMatrix\n" +
            "\n" +
            "\tplayer:\n" +
            "\t\t115: BotCancelPathFinding\n" +
            "\t\t116: BotLoopIterator1\n" +
            "\t\t117: BotTempArray\n" +
            "\t\t118: BotTargetPlayer\n" +
            "\t\t119: BotTargetPosition\n" +
            "\t\t120: BotClosestNodeIdToTarget\n" +
            "\t\t121: BotClosestNodeIdToBot\n" +
            "\t\t122: BotPrevNodeId\n" +
            "\t\t123: BotNextNodeId\n" +
            "\t\t124: BotNextNodePosition\n" +
            "\t\t125: BotCurrentDistanceToTarget\n" +
            "\t\t126: BotShortestDistanceToTarget\n" +
            "\t\t127: BotIsPathFinding\n" +
            "}";

        VariableBlock variableBlock = new VariableBlock();

        // Global vars
        variableBlock.addGlobal(new Variable<Array<Vector>>(125, "NodePositions"));
        variableBlock.addGlobal(new Variable<Array<Vector>>(126, "NodeConnections"));
        variableBlock.addGlobal(new Variable<Array<Vector>>(127, "DistanceMatrix"));

        // Player vars
        variableBlock.addPlayer(new Variable(115, "BotCancelPathFinding"));
        variableBlock.addPlayer(new Variable(116, "BotLoopIterator1"));
        variableBlock.addPlayer(new Variable(117, "BotTempArray"));
        variableBlock.addPlayer(new Variable(118, "BotTargetPlayer"));
        variableBlock.addPlayer(new Variable(119, "BotTargetPosition"));
        variableBlock.addPlayer(new Variable(120, "BotClosestNodeIdToTarget"));
        variableBlock.addPlayer(new Variable(121, "BotClosestNodeIdToBot"));
        variableBlock.addPlayer(new Variable(122, "BotPrevNodeId"));
        variableBlock.addPlayer(new Variable(123, "BotNextNodeId"));
        variableBlock.addPlayer(new Variable(124, "BotNextNodePosition"));
        variableBlock.addPlayer(new Variable(125, "BotCurrentDistanceToTarget"));
        variableBlock.addPlayer(new Variable(126, "BotShortestDistanceToTarget"));
        variableBlock.addPlayer(new Variable(127, "BotIsPathFinding"));

        // When
        String actualResult = variableBlock.toString();

        // Then
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Only global vars code snippet")
    void onlyGlobalVarsTest()
    {
        // Given
        String expectedResult =
            "variables\n" +
            "{\n" +
            "\tglobal:\n" +
            "\t\t125: NodePositions\n" +
            "\t\t126: NodeConnections\n" +
            "\t\t127: DistanceMatrix\n" +
            "}";

        VariableBlock variableBlock = new VariableBlock();

        // Global vars
        variableBlock.addGlobal(new Variable<Array<Vector>>(125, "NodePositions"));
        variableBlock.addGlobal(new Variable<Array<Vector>>(126, "NodeConnections"));
        variableBlock.addGlobal(new Variable<Array<Vector>>(127, "DistanceMatrix"));

        // When
        String actualResult = variableBlock.toString();

        // Then
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Only player vars code snippet")
    void onlyPlayerVarsTest()
    {
        // Given
        String expectedResult =
                "variables\n" +
                "{\n" +
                "\tplayer:\n" +
                "\t\t115: BotCancelPathFinding\n" +
                "\t\t116: BotLoopIterator1\n" +
                "\t\t117: BotTempArray\n" +
                "\t\t118: BotTargetPlayer\n" +
                "\t\t119: BotTargetPosition\n" +
                "\t\t120: BotClosestNodeIdToTarget\n" +
                "\t\t121: BotClosestNodeIdToBot\n" +
                "\t\t122: BotPrevNodeId\n" +
                "\t\t123: BotNextNodeId\n" +
                "\t\t124: BotNextNodePosition\n" +
                "\t\t125: BotCurrentDistanceToTarget\n" +
                "\t\t126: BotShortestDistanceToTarget\n" +
                "\t\t127: BotIsPathFinding\n" +
                "}";

        VariableBlock variableBlock = new VariableBlock();

        // Player vars
        variableBlock.addPlayer(new Variable(115, "BotCancelPathFinding"));
        variableBlock.addPlayer(new Variable(116, "BotLoopIterator1"));
        variableBlock.addPlayer(new Variable(117, "BotTempArray"));
        variableBlock.addPlayer(new Variable(118, "BotTargetPlayer"));
        variableBlock.addPlayer(new Variable(119, "BotTargetPosition"));
        variableBlock.addPlayer(new Variable(120, "BotClosestNodeIdToTarget"));
        variableBlock.addPlayer(new Variable(121, "BotClosestNodeIdToBot"));
        variableBlock.addPlayer(new Variable(122, "BotPrevNodeId"));
        variableBlock.addPlayer(new Variable(123, "BotNextNodeId"));
        variableBlock.addPlayer(new Variable(124, "BotNextNodePosition"));
        variableBlock.addPlayer(new Variable(125, "BotCurrentDistanceToTarget"));
        variableBlock.addPlayer(new Variable(126, "BotShortestDistanceToTarget"));
        variableBlock.addPlayer(new Variable(127, "BotIsPathFinding"));

        // When
        String actualResult = variableBlock.toString();

        // Then
        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }
}
