package com.hukuta94.pathnodebuilder.calculator.filter

import com.hukuta94.pathnodebuilder.calculator.filter.dto.DistanceMatrixDto
import com.hukuta94.pathnodebuilder.common.type.Vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ComputeFullDistanceMatrixFilterTest {

    private val filter = ComputeUnitDistanceMatrixFilter()

    @Test
    fun `should compute full distance matrix`() {
        // Given:
        val distanceMatrixDto = DistanceMatrixDto(
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
        val actualResult = filter.apply(distanceMatrixDto)

        // Then:
        val expectedDistanceMatrix = listOf(
            doubleArrayOf(0.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 2.0),
            doubleArrayOf(1.0, 0.0, 1.0, 2.0, 3.0, 2.0, 2.0, 3.0),
            doubleArrayOf(2.0, 1.0, 0.0, 1.0, 2.0, 3.0, 2.0, 3.0),
            doubleArrayOf(2.0, 2.0, 1.0, 0.0, 1.0, 2.0, 1.0, 2.0),
            doubleArrayOf(2.0, 3.0, 2.0, 1.0, 0.0, 1.0, 2.0, 1.0),
            doubleArrayOf(1.0, 2.0, 3.0, 2.0, 1.0, 0.0, 2.0, 1.0),
            doubleArrayOf(1.0, 2.0, 2.0, 1.0, 2.0, 2.0, 0.0, 1.0),
            doubleArrayOf(2.0, 3.0, 3.0, 2.0, 1.0, 1.0, 1.0, 0.0)
        )
        assert2DArrayEquals(expectedDistanceMatrix, actualResult.matrix)
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