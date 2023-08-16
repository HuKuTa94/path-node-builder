package com.hukuta94.pathnodebuilder.pipeline.filter.calculator

import java.util.function.Function

/**
 * Removes diagonal zero elements and half of the bottom part of computed distance matrix.
 * Leaves only upper diagonal.
 *
 * Example
 * - Input distance matrix:
 *    - (0, 1, 2, 2)
 *    - (1, 0, 1, 2)
 *    - (2, 1, 0, 1)
 *    - (2, 2, 1, 0)
 *
 * - Output distance matrix:
 *    - (1, 2, 2)
 *    - (   1, 2)
 *    - (      1)
 */
class RemoveLowerMatrixDiagonalFilter : Function<DistanceMatrixData, DistanceMatrixData> {

    override fun apply(input: DistanceMatrixData): DistanceMatrixData {
        val matrix = input.matrix
        val newMatrixSize = matrix.size - 1

        val newMatrix = matrix.subList(0, newMatrixSize)
            .mapIndexed { i, array ->
                val newStartIndex = 1 + i
                val newArraySize = array.size - 1

                array.sliceArray(newStartIndex .. newArraySize)
            }

        return input.copy(
            matrix = newMatrix
        )
    }
}