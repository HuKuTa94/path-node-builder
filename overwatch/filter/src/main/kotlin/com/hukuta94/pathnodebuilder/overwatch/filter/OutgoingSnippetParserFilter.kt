package com.hukuta94.pathnodebuilder.overwatch.filter

import com.hukuta94.pathnodebuilder.common.type.Vector
import com.hukuta94.pathnodebuilder.calculator.filter.dto.DistanceMatrixDto
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_DISTANCE_MATRIX_VAR_INDEX
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_DISTANCE_MATRIX_VAR_NAME
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_NODE_CONNECTIONS_VAR_INDEX
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_NODE_CONNECTIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_NODE_POSITIONS_VAR_INDEX
import com.hukuta94.pathnodebuilder.overwatch.filter.OverwatchGlobalVariables.OUTPUT_NODE_POSITIONS_VAR_NAME
import java.util.StringJoiner
import java.util.function.Function

class OutgoingSnippetParserFilter : Function<DistanceMatrixDto, String> {
    override fun apply(input: DistanceMatrixDto): String {
        val builder = StringJoiner("\n")

        builder.add(VARIABLE_BLOCK)
        builder.add(buildActionBlock(input))

        return builder.toString()
    }

    private fun buildActionBlock(distanceMatrixDto: DistanceMatrixDto) = """
actions
{
    Global.${OUTPUT_NODE_POSITIONS_VAR_NAME} = ${convertNodePositions(distanceMatrixDto.positions)}
    Global.${OUTPUT_NODE_CONNECTIONS_VAR_NAME} = ${convertNodeConnections(distanceMatrixDto.connections)}
    Global.${OUTPUT_DISTANCE_MATRIX_VAR_NAME} = ${convertDistanceMatrix(distanceMatrixDto.matrix)}
}
""".trimMargin()

    private fun convertNodePositions(elements: List<Vector>): String {
        return convertToString(elements) { i ->
            "Vector(${elements[i].x}, ${elements[i].y}, ${elements[i].z})"
        }
    }

    private fun convertNodeConnections(elements: List<IntArray>): String {
        return convertToString(elements) { i ->
            val builder = StringBuilder()
            builder.append("Array(")

            for (j in elements[i].indices) {
                builder.append(elements[i][j])
                if (j != elements[i].size - 1) {
                    //TODO лучше заюзать StringJoiner
                    builder.append(", ")
                } else {
                    builder.append(")")
                }
            }

            builder.toString()
        }
    }

    private fun convertDistanceMatrix(elements: List<DoubleArray>): String {
        return convertToString(elements) { i ->
            val builder = StringBuilder()
            builder.append("Array(")

            for (j in elements[i].indices) {
                builder.append(elements[i][j])
                if (j != elements[i].size - 1) {
                    //TODO лучше заюзать StringJoiner
                    builder.append(", ")
                } else {
                    builder.append(")")
                }
            }

            builder.toString()
        }
    }

    /**
     * Converts list of elements into string of Overwatch Workshop format
     */
    private fun <T> convertToString(
        elements: List<T>,
        convertFunction: (elementIndex: Int) -> String
    ): String {
        val elementsSize = elements.size
        if (elementsSize == 0) {
            return EMPTY_ARRAY
        }

        // Begin fill array
        val builder = StringBuilder()
        builder.append("Array(\n")

        val lastElementIndex = elementsSize - 1
        for (i in 0 until elementsSize) {
            builder.append(
                "\t\t${convertFunction.invoke(i)}"
            )

            // Don't put ", " if it is the last element
            if (i != lastElementIndex) {
                builder.append(",\n")
            }
        }
        builder.append(");")

        return builder.toString()
    }

    companion object {
        private val VARIABLE_BLOCK = """
        variables
        {
            global:
                $OUTPUT_NODE_POSITIONS_VAR_INDEX: $OUTPUT_NODE_POSITIONS_VAR_NAME
                $OUTPUT_NODE_CONNECTIONS_VAR_INDEX: $OUTPUT_NODE_CONNECTIONS_VAR_NAME
                $OUTPUT_DISTANCE_MATRIX_VAR_INDEX: $OUTPUT_DISTANCE_MATRIX_VAR_NAME
        }
    """.trimIndent()

        private const val EMPTY_ARRAY = "Array();"
    }
}