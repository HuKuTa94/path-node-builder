package com.hukuta94.pathnodebuilder.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class DistanceMatrixTest
{
    @Test
    @DisplayName("Init default not optimized distance matrix")
    void initDefaultMatrixTest()
    {
        // Given
        int matrixSize = 10;

        // When
        DistanceMatrix distanceMatrix = new DistanceMatrix(matrixSize, false);

        // Then
        Assertions.assertFalse(distanceMatrix.isOptimized());

        for (int[] matrix : distanceMatrix.matrix)
        {
            Assertions.assertEquals(matrixSize, matrix.length);
        }
    }

    @Test
    @DisplayName("Init optimized distance matrix")
    void initOptimizedMatrixTest()
    {
        // Given
        int matrixSize = 10;

        // When
        DistanceMatrix distanceMatrix = new DistanceMatrix(matrixSize, true);

        // Then
        Assertions.assertTrue(distanceMatrix.isOptimized());

        int yExpectedMatrixLength = matrixSize;
        for (int[] matrix : distanceMatrix.matrix)
        {
            Assertions.assertEquals(yExpectedMatrixLength, matrix.length);
            yExpectedMatrixLength--;
        }
    }

    @Test
    @DisplayName("Setter getter test")
    void setGetValueTest()
    {
        // Given
        int matrixSize = 10;
        int expectedValue = 5;
        DistanceMatrix defaultMatrix = new DistanceMatrix(matrixSize, false);

        // Then
        // Setter
        Assertions.assertDoesNotThrow(() -> defaultMatrix.setValue(0, 0, expectedValue));
        Assertions.assertDoesNotThrow(() -> defaultMatrix.setValue(matrixSize-1, matrixSize-1, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(-1, 0, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(0, -1, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(-1, -1, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(matrixSize, 0, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(0, matrixSize, expectedValue));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.setValue(matrixSize, matrixSize, expectedValue));

        // Getter
        Assertions.assertEquals(expectedValue, defaultMatrix.getValue(0, 0));
        Assertions.assertEquals(expectedValue, defaultMatrix.getValue(matrixSize-1, matrixSize-1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(-1, 0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(0, -1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(-1, -1));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(matrixSize, 0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(0, matrixSize));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> defaultMatrix.getValue(matrixSize, matrixSize));
    }
}
