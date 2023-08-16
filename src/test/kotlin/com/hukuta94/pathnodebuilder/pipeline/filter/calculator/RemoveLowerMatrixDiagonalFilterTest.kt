package com.hukuta94.pathnodebuilder.pipeline.filter.calculator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class RemoveLowerMatrixDiagonalFilterTest {

    private val filter = RemoveLowerMatrixDiagonalFilter()

    @Test
    fun `should remove lower diagonal from full computed distance matrix`() {
        // Given
        val computedFullDistanceMatrix = DistanceMatrixData(
            positions = emptyList(),
            connections = emptyList(),
            matrix = listOf(
                intArrayOf(0, 1, 2, 2, 2, 1, 1, 2),
                intArrayOf(1, 0, 1, 2, 3, 2, 2, 3),
                intArrayOf(2, 1, 0, 1, 2, 3, 2, 3),
                intArrayOf(2, 2, 1, 0, 1, 2, 1, 2),
                intArrayOf(2, 3, 2, 1, 0, 1, 2, 1),
                intArrayOf(1, 2, 3, 2, 1, 0, 2, 1),
                intArrayOf(1, 2, 2, 1, 2, 2, 0, 1),
                intArrayOf(2, 3, 3, 2, 1, 1, 1, 0)
            )
        )

        // When
        val actualResult = filter.apply(computedFullDistanceMatrix)

        // Then
        val expectedDistanceMatrixWithoutLowerDiagonal = listOf(
            intArrayOf(1, 2, 2, 2, 1, 1, 2),
            intArrayOf(   1, 2, 3, 2, 2, 3),
            intArrayOf(      1, 2, 3, 2, 3),
            intArrayOf(         1, 2, 1, 2),
            intArrayOf(            1, 2, 1),
            intArrayOf(               2, 1),
            intArrayOf(                  1)
        )

        assert2DIntArrayEquals(expectedDistanceMatrixWithoutLowerDiagonal, actualResult.matrix)
    }

    private fun assert2DIntArrayEquals(expected: List<IntArray>, actual: List<IntArray>) {
        assertEquals(expected.size, actual.size)

        expected.forEachIndexed { i, expectedInts ->
            val actualInts = actual[i]

            assertNotNull(actualInts)
            assertEquals(expectedInts.size, actualInts.size)

            expectedInts.forEachIndexed { j, expectedInt ->
                assertEquals(expectedInt, actualInts[j])
            }
        }
    }
}