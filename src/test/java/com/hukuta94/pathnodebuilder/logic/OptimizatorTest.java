package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.OverwatchParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OptimizatorTest
{
    @Autowired
    OverwatchParser parser;
    @Autowired
    Optimizator optimizator;

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
    @DisplayName("Optimize input data without compressing arrays")
    void optimizeInputDataWithoutCompressingArrays()
    {
        // Given:
        Vector[] inputPositions = new Vector[]
            {                               // node indexes
                null,                       // 0
                new Vector(9, 0, 3), // 1
                new Vector(2, 0, 8), // 2
                new Vector(6, 0, 1), // 3
                null,                       // 4
                new Vector(7, 0, 4), // 5
                null,                       // 6
                new Vector(3, 2, 1), // 7
                null                        // 8
            };

        int[][] inputConnections = new int[][]
            {           // node indexes
                null,   // 0
                {2, 5}, // 1
                {1, 3}, // 2
                {2, 5}, // 3
                null,   // 4
                {1, 3}, // 5
                null,   // 6
                {3, 5}, // 7
                null    // 8
            };

        Vector[] expectedPositions = new Vector[]
            {                               // node indexes
                new Vector(9, 0, 3), // 0
                new Vector(2, 0, 8), // 1
                new Vector(6, 0, 1), // 2
                new Vector(7, 0, 4), // 3
                new Vector(3, 2, 1), // 4
                null,                       // 5
                null,                       // 6
                null,                       // 7
                null,                       // 8
            };

        int[][] expectedConnections = new int[][]
            {           // node indexes
                {1, 3}, // 0
                {0, 2}, // 1
                {1, 3}, // 2
                {0, 2}, // 3
                {2, 3}, // 4
                null,   // 5
                null,   // 6
                null,   // 7
                null,   // 8
            };

        // When:
        Tuple<Vector[], int[][]> result =
                optimizator.optimizeInputData(new Tuple<>(inputPositions, inputConnections), false);

        Vector[] actualPositions = result.getObjectA();
        int[][] actualConnections = result.getObjectB();

        // Then:
        assertEquals(expectedPositions.length, actualPositions.length);
        assertEquals(expectedConnections.length, actualConnections.length);

        assertPositionArrays(expectedPositions, actualPositions);
        assertConnectionArrays(expectedConnections, actualConnections);
    }

    @Test
    @DisplayName("Optimize input data with compressing arrays")
    void optimizeInputDataWithCompressingArrays()
    {
        // Given:
        Vector[] inputPositions = new Vector[]
        {                                   // node indexes
                null,                       // 0
                new Vector(9, 0, 3), // 1
                new Vector(2, 0, 8), // 2
                new Vector(6, 0, 1), // 3
                null,                       // 4
                new Vector(7, 0, 4), // 5
                null,                       // 6
                new Vector(3, 2, 1), // 7
                null                        // 8
            };

        int[][] inputConnections = new int[][]
            {           // node indexes
                null,   // 0
                {2, 5}, // 1
                {1, 3}, // 2
                {2, 5}, // 3
                null,   // 4
                {1, 3}, // 5
                null,   // 6
                {3, 5}, // 7
                null    // 8
            };

        Vector[] expectedPositions = new Vector[]
            {                               // node indexes
                new Vector(9, 0, 3), // 0
                new Vector(2, 0, 8), // 1
                new Vector(6, 0, 1), // 2
                new Vector(7, 0, 4), // 3
                new Vector(3, 2, 1), // 4
            };

        int[][] expectedConnections = new int[][]
            {           // node indexes
                {1, 3}, // 0
                {0, 2}, // 1
                {1, 3}, // 2
                {0, 2}, // 3
                {2, 3}, // 4
            };

        // When:
        Tuple<Vector[], int[][]> result =
                optimizator.optimizeInputData(new Tuple<>(inputPositions, inputConnections), true);

        Vector[] actualPositions = result.getObjectA();
        int[][] actualConnections = result.getObjectB();

        // Then:
        assertEquals(expectedPositions.length, actualPositions.length);
        assertEquals(expectedConnections.length, actualConnections.length);

        assertPositionArrays(expectedPositions, actualPositions);
        assertConnectionArrays(expectedConnections, actualConnections);
    }
}
