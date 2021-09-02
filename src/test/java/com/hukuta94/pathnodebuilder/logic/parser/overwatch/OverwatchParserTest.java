package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.logic.parser.ParserHelper;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // Variable type declaration
        Variable<Array<Vector>> builderNodePositionsVar = new Variable<>(17, "BuilderNodePositions");
        Variable<Array<Array<Integer>>> builderNodeConnectionsVar = new Variable<>(18, "BuilderNodeConnections");

        // Variable value definition
        Array<Vector> builderNodePositionsValue = new Array<>(10);
        builderNodePositionsValue.add(new Vector(-16.004, 0.35, -15.965));
        builderNodePositionsValue.add(new Vector(-15.994, 0.35, -0.043));
        builderNodePositionsValue.add(new Vector(-15.981, 0.35, 15.908));
        builderNodePositionsValue.add(new Vector(15.941, 0.35, 16.021));
        builderNodePositionsValue.add(new Vector(15.98 , 0.35, -15.878));
        builderNodePositionsValue.add(new Vector(-1.423, 0.35, -16.035));
        builderNodePositionsValue.add(new Vector(-1.225, 0.35, 4.747));
        builderNodePositionsValue.add(new Vector(7.259, 0.35, -0.358));
        builderNodePositionsValue.add(null);
        builderNodePositionsValue.add(new Vector(-7.908, 0.425, 10.12));
        builderNodePositionsVar.setValue(builderNodePositionsValue);

        Array<Array<Integer>> builderNodeConnectionsValue = new Array<>(9);
        builderNodeConnectionsValue.add(new Array<>(5, 1, 6));
        builderNodeConnectionsValue.add(new Array<>(0, 2));
        builderNodeConnectionsValue.add(new Array<>(3, 1));
        builderNodeConnectionsValue.add(new Array<>(2, 6, 4));
        builderNodeConnectionsValue.add(new Array<>(5, 3, 7));
        builderNodeConnectionsValue.add(new Array<>(4, 0, 7));
        builderNodeConnectionsValue.add(new Array<>(0, 3, 7));
        builderNodeConnectionsValue.add(new Array<>(6, 5, 4));
        builderNodeConnectionsValue.add(null);
        builderNodeConnectionsVar.setValue(builderNodeConnectionsValue);

        // Build final code snippet of the workshop's rule
        RuleActionCodeSnippet expectedResult = new RuleActionCodeSnippet();
        expectedResult.addGlobals(builderNodePositionsVar, builderNodeConnectionsVar);

        String inputString = ParserHelper.loadTestFile("overwatch/1/", null);

        // When
        RuleActionCodeSnippet actualResult = overwatchParser.parseInputData(inputString);

        // Then
        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
