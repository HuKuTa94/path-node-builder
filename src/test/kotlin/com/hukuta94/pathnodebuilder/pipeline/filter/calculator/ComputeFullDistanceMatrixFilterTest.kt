package com.hukuta94.pathnodebuilder.pipeline.filter.calculator

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ComputeFullDistanceMatrixFilterTest {

    private val filter = ComputeFullDistanceMatrixFilter()

    @Test
    fun `should compute full distance matrix`() {
        // Given:
        val distanceMatrixData = DistanceMatrixData(
            positions = listOf(
                Vector(-16.004, 0.350, -15.965),
                Vector(-15.994, 0.350, -0.043),
                Vector(-15.981, 0.350, 15.908),
                Vector(15.941, 0.350, 16.021),
                Vector(15.980, 0.350, -15.878),
                Vector(-1.423, 0.350, -16.035),
                Vector(-1.225, 0.350, 4.747),
                Vector(7.259, 0.350, -0.358)
            ),
            connections = listOf(
                intArrayOf(5, 1, 6),
                intArrayOf(0, 2),
                intArrayOf(3, 1),
                intArrayOf(2, 6, 4),
                intArrayOf(5, 3, 7),
                intArrayOf(4, 0, 7),
                intArrayOf(0, 3, 7),
                intArrayOf(6, 5, 4)
            ),
            matrix = emptyList()
        )

        // When:
        val actualResult = filter.apply(distanceMatrixData)

        // Then:
        val expectedDistanceMatrix = listOf(
            intArrayOf(0, 1, 2, 2, 2, 1, 1, 2),
            intArrayOf(1, 0, 1, 2, 3, 2, 2, 3),
            intArrayOf(2, 1, 0, 1, 2, 3, 2, 3),
            intArrayOf(2, 2, 1, 0, 1, 2, 1, 2),
            intArrayOf(2, 3, 2, 1, 0, 1, 2, 1),
            intArrayOf(1, 2, 3, 2, 1, 0, 2, 1),
            intArrayOf(1, 2, 2, 1, 2, 2, 0, 1),
            intArrayOf(2, 3, 3, 2, 1, 1, 1, 0)
        )
        assert2DIntArrayEquals(expectedDistanceMatrix, actualResult.matrix)
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