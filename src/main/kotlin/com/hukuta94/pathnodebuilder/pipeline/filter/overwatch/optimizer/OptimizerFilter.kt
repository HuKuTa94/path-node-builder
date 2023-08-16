package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.optimizer

import com.hukuta94.pathnodebuilder.pipeline.filter.calculator.DistanceMatrixData
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming.ParsedIncomingData
import java.util.function.Function

class OptimizerFilter : Function<ParsedIncomingData, DistanceMatrixData> {

    override fun apply(incomingData: ParsedIncomingData): DistanceMatrixData {
        val inputPositions = incomingData.builderNodePositions
        val inputConnections = incomingData.builderNodeConnections

        val outputPositions = arrayOfNulls<Vector>(inputPositions.size)
        val outputConnections = arrayOfNulls<IntArray>(inputConnections.size)

        var emptySpaceCount = 0
        var oldElementIndex: Int
        var newElementIndex: Int

        // Begin optimization input data
        for (i in inputPositions.indices) {

            // Count of 'null' elements between side elements (also skips the nodes without any connections)
            if (inputConnections[i] == null) {
                emptySpaceCount++
                continue
            }

            // Save 'old' index to update connection arrays
            oldElementIndex = i

            // Calculate index for a new element if we have empty spaces between side elements
            newElementIndex = if (emptySpaceCount == 0) i else i - emptySpaceCount

            // Copy values from input to output array
            outputPositions[newElementIndex] = inputPositions[oldElementIndex]
            outputConnections[newElementIndex] = inputConnections[oldElementIndex]

            // Go to next iteration if we don't have spaces between side elements
            if (emptySpaceCount == 0 && outputConnections[0] != null) {
                continue
            }

            // Update indexes in result output connection array in range from 0 to current iterator (i-1)
            for (j in 0 until i - 1) {
                // Skip nullable element
                if (outputConnections[j] == null) {
                    continue
                }

                // Iterate inner output array of node connections
                val innerArray = requireNotNull(outputConnections[j])
                for (k in innerArray.indices) {
                    // Update old index to new
                    if (innerArray[k] == oldElementIndex) {
                        innerArray[k] = newElementIndex
                    }
                }
            }

            // Update next indexes in input connection array from current iterator (i+1) position
            for (j in i + 1 until inputConnections.size) {
                // Skip nullable element
                if (inputConnections[j] == null) {
                    continue
                }

                // Iterate inner input array of node connections
                val innerArray = requireNotNull(inputConnections[j])
                for (k in innerArray.indices) {
                    // Update old index to new
                    if (innerArray[k] == oldElementIndex) {
                        innerArray[k] = newElementIndex
                    }
                }
            }
        }

        return DistanceMatrixData(
            positions = outputPositions.mapNotNull { it },
            connections = outputConnections.mapNotNull { it },
            matrix = emptyList()
        )
    }
}