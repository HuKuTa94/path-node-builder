package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.logic.parser.ParserHelper;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OverwatchParserTest
{
    @Autowired
    private OverwatchParser overwatchParser;

    @Test
    @DisplayName("Parse input data")
    void parseInputDataTest() throws Exception
    {
        // Given
        // Variables block
        Variables vars = new Variables();
        vars.addGlobal(125, "NodePositions");
        vars.addGlobal(126, "NodeConnections");

        // Actions block
        // Node positions as array of vectors
        Array<Vector> nodePositions = new Array<>(1);
        nodePositions.add(new Vector(-16.004, 0.35, -15.965));
        nodePositions.add(new Vector(-15.994, 0.35, -0.043));
        nodePositions.add(new Vector(-15.981, 0.35, 15.908));
        nodePositions.add(new Vector(15.941, 0.35, 16.021));
        nodePositions.add(new Vector(15.98 , 0.35, -15.878));
        nodePositions.add(new Vector(-1.423, 0.35, -16.035));
        nodePositions.add(new Vector(-1.225, 0.35, 4.747));
        nodePositions.add(new Vector(7.259, 0.35, -0.358));
        nodePositions.add(new Vector(-7.908, 0.425, 10.12));

        // Node connections as 2D array
        Array<Array> nodeConnections = new Array<>(8);
        nodeConnections.add(new Array<>(5, 1, 6));
        nodeConnections.add(new Array<>(0, 2));
        nodeConnections.add(new Array<>(3, 1));
        nodeConnections.add(new Array<>(2, 6, 4));
        nodeConnections.add(new Array<>(5, 3, 7));
        nodeConnections.add(new Array<>(4, 0, 7));
        nodeConnections.add(new Array<>(0, 3, 7));
        nodeConnections.add(new Array<>(6, 5, 4));

        Actions actions = new Actions();
        actions.add(new Variable<>(125, "NodePositions", nodePositions));
        actions.add(new Variable<>(126, "NodeConnections", nodeConnections));

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet expectedResult = new RuleActionCodeSnippet(vars, actions);

        String inputString = ParserHelper.loadTestFile("overwatch/1/", null);

        // When
        RuleActionCodeSnippet actualResult = overwatchParser.parseInputData(inputString);

        // Then
        Assertions.assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
