package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.outgoing

import com.hukuta94.pathnodebuilder.pipeline.filter.calculator.DistanceMatrixData
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector
import java.util.function.Function

class OutgoingParserFilter : Function<DistanceMatrixData, String> {

    override fun apply(input: DistanceMatrixData): String {
        val outputPositions = input.positions
        val outputConnections = input.connections
        val distanceMatrix = input.matrix

        val builder = StringBuilder()
        // Variables block
        builder.append("variables\n{\n\tglobal:\n\t\t")
        builder.append("125")
        builder.append(": ")
        builder.append("NodePositions")
        builder.append("\n\t\t")
        builder.append("126")
        builder.append(": ")
        builder.append("NodeConnections")
        builder.append("\n\t\t")
        builder.append("127")
        builder.append(": ")
        builder.append("DistanceMatrix")

        // Actions block
        builder.append("\n}\nactions\n{\n")

        // Node positions
        builder.append("\tGlobal.")
        builder.append("NodePositions")
        builder.append(" =\n")
        convertNodePositions(builder, outputPositions)
        builder.append("\n")

        // Node connections
        builder.append("\tGlobal.")
        builder.append("NodeConnections")
        builder.append(" =\n")
        convert2DArray(builder, outputConnections)
        builder.append("\n")

        // Distance matrix
        builder.append("\tGlobal.")
        builder.append("DistanceMatrix")
        builder.append(" =\n")
        convertMatrix(builder, distanceMatrix)
        builder.append("\n}\n")

        return builder.toString()
    }

    private fun convertNodePositions(builder: StringBuilder, elements: List<Vector>) {
        val elementsSize = elements.size
        if (elementsSize == 0) {
            builder.append("\t\tArray();")
            return
        }

        // Begin fill array
        builder.append("\t\tArray(\n")
        for (i in 0 until elementsSize) {
            builder.append("\t\t\tVector(")
            builder.append(elements[i].x)
            builder.append(", ")
            builder.append(elements[i].y)
            builder.append(", ")
            builder.append(elements[i].z)
            builder.append(")")

            // Dont put ", " if it is the last element
            if (i != elementsSize - 1) {
                builder.append(",\n")
            }
        }
        builder.append(");")
    }

    private fun convert2DArray(builder: StringBuilder, elements: List<IntArray>) {
        val elementsSize = elements.size
        if (elementsSize == 0) {
            builder.append("\t\tArray();")
            return
        }

        // Begin fill array
        builder.append("\t\tArray(\n")
        for (i in 0 until elementsSize) {
            builder.append("\t\t\tArray(")
            for (j in elements[i].indices) {
                builder.append(elements[i][j])
                if (j != elements[i].size - 1) {
                    builder.append(", ")
                } else {
                    builder.append(")")
                }
            }

            // Don't put ", " if it is the last element
            if (i != elementsSize - 1) {
                builder.append(",\n")
            }
        }
        builder.append(");")
    }

    private fun convertMatrix(builder: StringBuilder, elements: List<DoubleArray>) {
        val elementsSize = elements.size
        if (elementsSize == 0) {
            builder.append("\t\tArray();")
            return
        }

        // Begin fill array
        builder.append("\t\tArray(\n")
        for (i in 0 until elementsSize) {
            builder.append("\t\t\tArray(")
            for (j in elements[i].indices) {
                builder.append(elements[i][j])
                if (j != elements[i].size - 1) {
                    builder.append(", ")
                } else {
                    builder.append(")")
                }
            }

            // Dont put ", " if it is the last element
            if (i != elementsSize - 1) {
                builder.append(",\n")
            }
        }
        builder.append(");")
    }
}