package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.logic.parser.ParserHelper;
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

    private void assertPositionArrays(Vector[] expected, Vector[] actual)
    {
        for (int i = 0; i < actual.length; i++)
        {
            // Ignore nullable elements
            if (expected[i] == null) {
                break;
            }

            assertEquals(expected[i].getX(), actual[i].getX());
            assertEquals(expected[i].getY(), actual[i].getY());
            assertEquals(expected[i].getZ(), actual[i].getZ());
        }
    }

    private void assertConnectionArrays(int[][] expected, int[][] actual)
    {
        // Connections
        for (int i = 0; i < actual.length; i++)
        {
            // Ignore nullable elements
            if (actual[i] == null) {
                break;
            }

            for (int j = 0; j < actual[i].length; j++)
            {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    @DisplayName("Parse input data")
    void parseInputDataTest() throws Exception
    {
        // Given
        Vector[] expectedPositions = new Vector[]
            {
                new Vector(-16.004, 0.350, -15.965),
                new Vector(-15.994, 0.350, -0.043),
                new Vector(-15.981, 0.350, 15.908),
                new Vector(15.941, 0.350, 16.021),
                new Vector(15.980, 0.350, -15.878),
                new Vector(-1.423, 0.350, -16.035),
                new Vector(-1.225, 0.350, 4.747),
                null,
                new Vector(7.259, 0.350, -0.358)
            };

        int[][] expectedConnections = new int[][]
            {
                {5, 1, 6},
                {0, 2},
                {3, 1},
                {2, 6, 4},
                {5, 3, 8},
                {4, 0, 8},
                {0, 3, 8},
                null,
                {6, 5, 4}
            };

        String inputString = ParserHelper.loadTestFile("overwatch/2/before/", null);

        // When
        Tuple<Vector[], int[][]> actualResult = overwatchParser.parseInputData(inputString);

        // Then
        assertPositionArrays(expectedPositions, actualResult.getObjectA());
        assertConnectionArrays(expectedConnections, actualResult.getObjectB());
    }

    @Test
    @DisplayName("Parse output data")
    void parseOutputDataTest() throws Exception
    {
        // Given
        Vector[] outputPositions = new Vector[]
                {
                        new Vector(-16.004, 0.350, -15.965),
                        new Vector(-15.994, 0.350, -0.043),
                        new Vector(-15.981, 0.350, 15.908),
                        new Vector(15.941, 0.350, 16.021),
                        new Vector(15.980, 0.350, -15.878),
                        new Vector(-1.423, 0.350, -16.035),
                        new Vector(-1.225, 0.350, 4.747),
                        new Vector(7.259, 0.350, -0.358)
                };

        int[][] outputConnections = new int[][]
                {
                        {5, 1, 6},
                        {0, 2},
                        {3, 1},
                        {2, 6, 4},
                        {5, 3, 7},
                        {4, 0, 7},
                        {0, 3, 7},
                        {6, 5, 4}
                };

        float[][] distanceMatrix = new float[][]
                {
                        {1, 2, 2, 2, 1, 1, 2},
                        {1, 2, 3, 2, 2, 3},
                        {1, 2, 3, 2, 3},
                        {1, 2, 1, 2},
                        {1, 2, 1},
                        {2, 1},
                        {1},
                };

        String expectedResult = ParserHelper.loadTestFile("overwatch/2/after/", null);

        // When
        String actualResult = overwatchParser.parseOutputData(outputPositions, outputConnections, distanceMatrix);

        // Then
        assertEquals(expectedResult, actualResult);
    }
}
