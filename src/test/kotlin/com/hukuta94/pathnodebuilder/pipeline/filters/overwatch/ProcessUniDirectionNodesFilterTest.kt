package com.hukuta94.pathnodebuilder.pipeline.filters.overwatch

import com.hukuta94.pathnodebuilder.pipeline.dto.DistanceMatrixDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ProcessUniDirectionNodesFilterTest {

    private val filter = ProcessUniDirectionNodesFilter()

    @Test
    fun `should combine two numbers of upper and lower of diagonal into one float number`() {
        // Given
        val computedDistanceMatrix = DistanceMatrixDto(
            positions = emptyList(),
            connections = emptyList(),
            matrix = listOf(
                doubleArrayOf(0.0, 1.0, 2.0, 2.0),
                doubleArrayOf(1.0, 0.0, 1.0, 2.0),
                doubleArrayOf(2.0, 1.0, 0.0, 3.0),
                doubleArrayOf(3.0, 2.0, 1.0, 0.0)
            )
        )

        // When
        val actualResult = filter.apply(computedDistanceMatrix)

        // Then
        val expectedDistanceMatrix = listOf(
            doubleArrayOf(0.000, 1.001, 2.002, 2.003),
            doubleArrayOf(1.001, 0.000, 1.001, 2.002),
            doubleArrayOf(2.002, 1.001, 0.000, 3.001),
            doubleArrayOf(3.002, 2.002, 1.003, 0.000)
        )

        assert2DArrayEquals(expectedDistanceMatrix, actualResult.matrix)
    }

    private fun assert2DArrayEquals(expected: List<DoubleArray>, actual: List<DoubleArray>) {
        Assertions.assertEquals(expected.size, actual.size)

        expected.forEachIndexed { i, expectedInts ->
            val actualInts = actual[i]

            Assertions.assertNotNull(actualInts)
            Assertions.assertEquals(expectedInts.size, actualInts.size)

            expectedInts.forEachIndexed { j, expectedInt ->
                Assertions.assertEquals(expectedInt, actualInts[j])
            }
        }
    }
}