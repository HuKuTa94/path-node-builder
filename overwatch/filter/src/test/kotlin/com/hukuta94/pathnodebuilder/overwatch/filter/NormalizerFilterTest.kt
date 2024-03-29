package com.hukuta94.pathnodebuilder.overwatch.filter

import com.hukuta94.pathnodebuilder.common.type.Vector
import com.hukuta94.pathnodebuilder.overwatch.filter.dto.GlobalvariablesDto
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class NormalizerFilterTest {

    private val filter = NormalizerFilter()

    @Test
    fun `should remove null values from arrays and recalculate indexes`() {
        // Given
        val builderNodePositions = listOf(
            // node indexes:
            /* 0 */ null,
            /* 1 */ Vector(9.0, 0.0, 3.0),
            /* 2 */ Vector(2.0, 0.0, 8.0),
            /* 3 */ Vector(6.0, 0.0, 1.0),
            /* 4 */ null,
            /* 5 */ Vector(7.0, 0.0, 4.0),
            /* 6 */ null,
            /* 7 */ Vector(3.0, 2.0, 1.0),
            /* 8 */ null
        )

        val builderNodeConnections = listOf(
            // node indexes:
            /* 0 */ null,
            /* 1 */ intArrayOf(2, 5),
            /* 2 */ intArrayOf(1, 3),
            /* 3 */ intArrayOf(2, 5),
            /* 4 */ null,
            /* 5 */ intArrayOf(1, 3),
            /* 6 */ null,
            /* 7 */ intArrayOf(3, 5),
            /* 8 */ null
        )

        val globalvariablesDto = GlobalvariablesDto(
            builderNodePositions = builderNodePositions,
            builderNodeConnections = builderNodeConnections
        )

        // When
        val actualResult = filter.apply(globalvariablesDto)

        // Then
        val expectedPositions = listOf(
            // node indexes:
            /* 0 */ Vector(9.0, 0.0, 3.0),
            /* 1 */ Vector(2.0, 0.0, 8.0),
            /* 2 */ Vector(6.0, 0.0, 1.0),
            /* 3 */ Vector(7.0, 0.0, 4.0),
            /* 4 */ Vector(3.0, 2.0, 1.0)
        )

        val expectedConnections = listOf(
            // node indexes:
            /* 0 */ intArrayOf(1, 3),
            /* 1 */ intArrayOf(0, 2),
            /* 2 */ intArrayOf(1, 3),
            /* 3 */ intArrayOf(0, 2),
            /* 4 */ intArrayOf(2, 3)
        )

        assertEquals(expectedPositions.size, actualResult.positions.size)
        assertEquals(expectedPositions, actualResult.positions)
        assert2DIntArrayEquals(expectedConnections, actualResult.connections)
    }

    @Test
    fun `should remove all nodes without connections`() {
        // Given
        val builderNodePositions = listOf(
            // node indexes:
            /* 0 */ null,
            /* 1 */ Vector(9.0, 0.0, 3.0),
            /* 2 */ Vector(6.0, 0.0, 1.0),
            /* 3 */ null,
            /* 4 */ Vector(7.0, 0.0, 4.0),  // <- node #4 has not connections
            /* 5 */ Vector(1.0, 2.0, 3.0)
        )

        val builderNodeConnections = listOf(
            // node indexes:
            /* 0 */ null,
            /* 1 */ intArrayOf(2, 5),
            /* 2 */ intArrayOf(1),
            /* 3 */ null,
            /* 4 */ null, // <- node #4 has not connections
            /* 5 */ intArrayOf(2)
        )

        val globalvariablesDto = GlobalvariablesDto(
            builderNodePositions = builderNodePositions,
            builderNodeConnections = builderNodeConnections
        )

        // When
        val actualResult = filter.apply(globalvariablesDto)

        // Then
        val expectedPositions = listOf(
            // node indexes:
            /* 0 */ Vector(9.0, 0.0, 3.0),
            /* 1 */ Vector(6.0, 0.0, 1.0),
            /* 2 */ Vector(1.0, 2.0, 3.0)
        )

        val expectedConnections = listOf(
            // node indexes:
            /* 0 */ intArrayOf(1, 2),
            /* 1 */ intArrayOf(0),
            /* 2 */ intArrayOf(1)
        )

        assertEquals(expectedPositions.size, actualResult.positions.size)
        assertEquals(expectedPositions, actualResult.positions)
        assert2DIntArrayEquals(expectedConnections, actualResult.connections)
    }

    @Test
    fun `should return same data content when optimization is not required`() {
        // Given
        val builderNodePositions = listOf(
            // node indexes:
            /* 0 */ Vector(9.0, 0.0, 3.0),
            /* 1 */ Vector(6.0, 0.0, 1.0)
        )

        val builderNodeConnections = listOf(
            // node indexes:
            /* 0 */ intArrayOf(1),
            /* 1 */ intArrayOf(0),
        )

        val globalvariablesDto = GlobalvariablesDto(
            builderNodePositions = builderNodePositions,
            builderNodeConnections = builderNodeConnections
        )

        // When
        val actualResult = filter.apply(globalvariablesDto)

        // Then
        assertEquals(builderNodePositions.size, actualResult.positions.size)
        assertEquals(builderNodePositions, actualResult.positions)
        assert2DIntArrayEquals(builderNodeConnections, actualResult.connections)
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