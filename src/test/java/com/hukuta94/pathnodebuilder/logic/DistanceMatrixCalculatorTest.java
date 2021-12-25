package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.common.types.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DistanceMatrixCalculatorTest
{
    @Autowired
    DistanceMatrixCalculator distanceMatrixCalculator;

    private Tuple<Vector[], int[][]> testData;

    private Tuple<Vector[], int[][]> getOptimizedTestData()
    {
        if (testData != null) {
            return testData;
        }

        Vector[] positions = new Vector[]
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

        int[][] connections = new int[][]
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

        testData = new Tuple<>(positions, connections);
        return testData;
    }

    private void assertDistanceMatrixArrays(int[][] expected, int[][] actual)
    {
        // Length of outer array
        assertEquals(expected.length, actual.length);

        for (int i = 0; i < actual.length; i++)
        {
            // Length of inner array
            assertEquals(expected[i].length, actual[i].length);

            // Values of inner elements
            for (int j = 0; j < actual[i].length; j++)
            {
                assertEquals(expected[i][j], actual[i][j]);
            }
        }
    }

    @Test
    @DisplayName("Default distance matrix (greedy algorithm)")
    void defaultDistanceMatrixTest()
    {
        // Given:
        int[][] expectedDistanceMatrix = new int[][]
            { // 0  1  2  3  4  5  6  7    y/x (node indexes)
                {0, 1, 2, 2, 2, 1, 1, 2},  // 0
                {1, 0, 1, 2, 3, 2, 2, 3},  // 1
                {2, 1, 0, 1, 2, 3, 2, 3},  // 2
                {2, 2, 1, 0, 1, 2, 1, 2},  // 3
                {2, 3, 2, 1, 0, 1, 2, 1},  // 4
                {1, 2, 3, 2, 1, 0, 2, 1},  // 5
                {1, 2, 2, 1, 2, 2, 0, 1},  // 6
                {2, 3, 3, 2, 1, 1, 1, 0},  // 7
            };

        // When:
        int[][] actualDistanceMatrix = distanceMatrixCalculator.calculate(getOptimizedTestData());

        // Then:
        assertDistanceMatrixArrays(expectedDistanceMatrix, actualDistanceMatrix);
    }

    @Test
    @DisplayName("Optimized distance matrix (lazy algorithm)")
    void optimizedDistanceMatrixTest()
    {
        // Given:
        int[][] expectedDistanceMatrix = new int[][]
            {
                {1, 2, 2, 2, 1, 1, 2},
                {1, 2, 3, 2, 2, 3},
                {1, 2, 3, 2, 3},
                {1, 2, 1, 2},
                {1, 2, 1},
                {2, 1},
                {1},
            };

        // When:
        int[][] actualDistanceMatrix = distanceMatrixCalculator.calculate(getOptimizedTestData());
        int[][] actualDistanceMatrixWithoutZeros = distanceMatrixCalculator.removeLowerDiagonalFromDistanceMatrix(actualDistanceMatrix);

        // Then:
        assertDistanceMatrixArrays(expectedDistanceMatrix, actualDistanceMatrixWithoutZeros);
    }
}
