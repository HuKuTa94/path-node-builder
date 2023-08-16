package com.hukuta94.pathnodebuilder.pipeline.filter.calculator

import java.util.*
import java.util.function.Function

class ComputeFullDistanceMatrixFilter : Function<DistanceMatrixData, DistanceMatrixData> {

    override fun apply(input: DistanceMatrixData): DistanceMatrixData {
        val outputPositions = input.positions
        val outputConnections = input.connections

        val distanceMatrix = Array(outputPositions.size) {
            DoubleArray(
                outputPositions.size
            )
        }

        // Calculate all distances for each node
        for (startNodeId in distanceMatrix.indices) {
            val queue = ArrayDeque<Int>()
            val visitedNodes = mutableSetOf<Int>()

            // Add start node id in the queue and mark it as visited
            queue.addLast(startNodeId)
            visitedNodes.add(startNodeId)

            // Visit all nodes
            while (!queue.isEmpty()) {
                // Poll first node from queue
                val currentNodeId = queue.pollFirst()

                // Visit neighbor nodes
                for (endNodeId in outputConnections[currentNodeId]) {
                    // Add neighbor node in queue and mark it as visited
                    if (!visitedNodes.contains(endNodeId)) {
                        queue.addLast(endNodeId)
                        visitedNodes.add(endNodeId)

                        // Accumulate distance to neighbor
                        distanceMatrix[startNodeId][endNodeId] = distanceMatrix[startNodeId][currentNodeId] + 1
                    }
                }
            }
        }

        return input.copy(
            matrix = distanceMatrix.toList()
        )
    }
}