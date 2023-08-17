package com.hukuta94.pathnodebuilder.overwatch.filter

import com.hukuta94.pathnodebuilder.calculator.filter.dto.DistanceMatrixDto
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
class RemoveLowerMatrixDiagonalFilter : Function<DistanceMatrixDto, DistanceMatrixDto> {

    override fun apply(input: DistanceMatrixDto): DistanceMatrixDto {
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