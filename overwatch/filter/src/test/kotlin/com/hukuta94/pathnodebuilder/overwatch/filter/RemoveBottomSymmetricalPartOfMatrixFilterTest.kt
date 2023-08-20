package com.hukuta94.pathnodebuilder.overwatch.filter

import com.hukuta94.pathnodebuilder.calculator.filter.dto.DistanceMatrixDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class RemoveBottomSymmetricalPartOfMatrixFilterTest {

    private val filter = RemoveBottomSymmetricalPartOfMatrixFilter()

    @Test
    fun `should remove lower diagonal from full computed distance matrix`() {
        // Given
        val computedFullDistanceMatrix = DistanceMatrixDto(
            positions = emptyList(),
            connections = emptyList(),
            matrix = listOf(
                doubleArrayOf(0.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 2.0),
                doubleArrayOf(1.0, 0.0, 1.0, 2.0, 3.0, 2.0, 2.0, 3.0),
                doubleArrayOf(2.0, 1.0, 0.0, 1.0, 2.0, 3.0, 2.0, 3.0),
                doubleArrayOf(2.0, 2.0, 1.0, 0.0, 1.0, 2.0, 1.0, 2.0),
                doubleArrayOf(2.0, 3.0, 2.0, 1.0, 0.0, 1.0, 2.0, 1.0),
                doubleArrayOf(1.0, 2.0, 3.0, 2.0, 1.0, 0.0, 2.0, 1.0),
                doubleArrayOf(1.0, 2.0, 2.0, 1.0, 2.0, 2.0, 0.0, 1.0),
                doubleArrayOf(2.0, 3.0, 3.0, 2.0, 1.0, 1.0, 1.0, 0.0)
            )
        )

        // When
        val actualResult = filter.apply(computedFullDistanceMatrix)

        // Then
        val expectedDistanceMatrixWithoutLowerDiagonal = listOf(
            doubleArrayOf(1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 2.0),
            doubleArrayOf(     1.0, 2.0, 3.0, 2.0, 2.0, 3.0),
            doubleArrayOf(          1.0, 2.0, 3.0, 2.0, 3.0),
            doubleArrayOf(               1.0, 2.0, 1.0, 2.0),
            doubleArrayOf(                    1.0, 2.0, 1.0),
            doubleArrayOf(                         2.0, 1.0),
            doubleArrayOf(                              1.0)
        )

        assert2DArrayEquals(expectedDistanceMatrixWithoutLowerDiagonal, actualResult.matrix)
    }

    private fun assert2DArrayEquals(expected: List<DoubleArray>, actual: List<DoubleArray>) {
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