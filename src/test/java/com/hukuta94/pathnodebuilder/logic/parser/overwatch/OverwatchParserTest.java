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
        Variable<Array<Array<Integer>>> BuilderNodeConnectionsVar = new Variable<>(18, "BuilderNodeConnections");

        // Variable value definition
        Array<Vector> builderNodePositionsVarValue = new Array<>(10);
        builderNodePositionsVarValue.add(new Vector(-16.004, 0.35, -15.965));
        builderNodePositionsVarValue.add(new Vector(-15.994, 0.35, -0.043));
        builderNodePositionsVarValue.add(new Vector(-15.981, 0.35, 15.908));
        builderNodePositionsVarValue.add(new Vector(15.941, 0.35, 16.021));
        builderNodePositionsVarValue.add(new Vector(15.98 , 0.35, -15.878));
        builderNodePositionsVarValue.add(new Vector(-1.423, 0.35, -16.035));
        builderNodePositionsVarValue.add(new Vector(-1.225, 0.35, 4.747));
        builderNodePositionsVarValue.add(new Vector(7.259, 0.35, -0.358));
        builderNodePositionsVarValue.add(null);
        builderNodePositionsVarValue.add(new Vector(-7.908, 0.425, 10.12));
        builderNodePositionsVar.setValue(builderNodePositionsVarValue);

        Array<Array<Integer>> BuilderNodeConnectionsVarValue = new Array<>(9);
        BuilderNodeConnectionsVarValue.add(new Array<>(5, 1, 6));
        BuilderNodeConnectionsVarValue.add(new Array<>(0, 2));
        BuilderNodeConnectionsVarValue.add(new Array<>(3, 1));
        BuilderNodeConnectionsVarValue.add(new Array<>(2, 6, 4));
        BuilderNodeConnectionsVarValue.add(new Array<>(5, 3, 7));
        BuilderNodeConnectionsVarValue.add(new Array<>(4, 0, 7));
        BuilderNodeConnectionsVarValue.add(new Array<>(0, 3, 7));
        BuilderNodeConnectionsVarValue.add(new Array<>(6, 5, 4));
        BuilderNodeConnectionsVarValue.add(null);
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
        RuleActionCodeSnippet expectedResult = new RuleActionCodeSnippet(variableBlock, actionBlock);

        String inputString = ParserHelper.loadTestFile("overwatch/1/", null);

        // When
        RuleActionCodeSnippet actualResult = overwatchParser.parseInputData(inputString);

        // Then
        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
