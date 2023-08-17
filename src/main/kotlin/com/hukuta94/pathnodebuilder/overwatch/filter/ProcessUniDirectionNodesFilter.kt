package com.hukuta94.pathnodebuilder.overwatch.filter

import com.hukuta94.pathnodebuilder.calculator.filter.dto.DistanceMatrixDto
import java.util.function.Function

/**
 * Uni-direction nodes can have two values of distance. The method collapse these values into one double number.
 *
 * Example: number 1 and number 4 will be collapsed into 1.004
 *
 * It means that:
 * distance from node A to node B equals 1, node A are connected to node B
 * distance from node B to node A equals 4, node B aren't connected to node A
 */
class ProcessUniDirectionNodesFilter : Function<DistanceMatrixDto, DistanceMatrixDto> {

    override fun apply(input: DistanceMatrixDto): DistanceMatrixDto {
        val matrix = input.matrix
        val matrixSize = matrix.size
        val newMatrix = Array(matrixSize) {
            DoubleArray(
                matrixSize
            )
        }

        for (row in 0 until matrixSize) {
            for (column in 0 until matrixSize) {
                val highDiagonalValue = matrix[row][column]
                val lowDiagonalValue = matrix[column][row]
                newMatrix[row][column] = collapseIntegerNumbersToFloat(highDiagonalValue, lowDiagonalValue)
            }
        }

        return input.copy(
            matrix = newMatrix.toList()
        )
    }

    /**
     * Collapses two integer numbers to double number.
     *
     * @param leftNumber - for example 1
     * @param rightNumber - for example 4
     *
     * @return double number 1.004
     */
    private fun collapseIntegerNumbersToFloat(
        leftNumber: Double,
        rightNumber: Double
    ): Double {
        return leftNumber + rightNumber / 1000
    }
}