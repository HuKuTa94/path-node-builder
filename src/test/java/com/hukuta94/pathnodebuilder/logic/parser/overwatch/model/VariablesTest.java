package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class VariablesTest
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

        Variables variables = new Variables();

        // Global vars
        variables.addGlobal(125, "NodePositions");
        variables.addGlobal(126, "NodeConnections");
        variables.addGlobal(127, "DistanceMatrix");

        // Player vars
        variables.addPlayer(115, "BotCancelPathFinding");
        variables.addPlayer(116, "BotLoopIterator1");
        variables.addPlayer(117, "BotTempArray");
        variables.addPlayer(118, "BotTargetPlayer");
        variables.addPlayer(119, "BotTargetPosition");
        variables.addPlayer(120, "BotClosestNodeIdToTarget");
        variables.addPlayer(121, "BotClosestNodeIdToBot");
        variables.addPlayer(122, "BotPrevNodeId");
        variables.addPlayer(123, "BotNextNodeId");
        variables.addPlayer(124, "BotNextNodePosition");
        variables.addPlayer(125, "BotCurrentDistanceToTarget");
        variables.addPlayer(126, "BotShortestDistanceToTarget");
        variables.addPlayer(127, "BotIsPathFinding");

        // When
        String actualResult = variables.toString();

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
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

        Variables variables = new Variables();

        // Global vars
        variables.addGlobal(125, "NodePositions");
        variables.addGlobal(126, "NodeConnections");
        variables.addGlobal(127, "DistanceMatrix");

        // When
        String actualResult = variables.toString();

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
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

        Variables variables = new Variables();

        // Player vars
        variables.addPlayer(115, "BotCancelPathFinding");
        variables.addPlayer(116, "BotLoopIterator1");
        variables.addPlayer(117, "BotTempArray");
        variables.addPlayer(118, "BotTargetPlayer");
        variables.addPlayer(119, "BotTargetPosition");
        variables.addPlayer(120, "BotClosestNodeIdToTarget");
        variables.addPlayer(121, "BotClosestNodeIdToBot");
        variables.addPlayer(122, "BotPrevNodeId");
        variables.addPlayer(123, "BotNextNodeId");
        variables.addPlayer(124, "BotNextNodePosition");
        variables.addPlayer(125, "BotCurrentDistanceToTarget");
        variables.addPlayer(126, "BotShortestDistanceToTarget");
        variables.addPlayer(127, "BotIsPathFinding");

        // When
        String actualResult = variables.toString();

        // Then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
