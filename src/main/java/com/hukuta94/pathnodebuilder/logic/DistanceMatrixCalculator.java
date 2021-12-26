package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * Calculator of distance matrix of the unit-edge graph
 */
@Service
public class DistanceMatrixCalculator
{
    /**
     * Calculates distance matrix from output optimized data (position array and connection array).
     * default matrix (with zeros):
     * {0, 1, 2, 3}
     * {1, 0, 4, 5}
     * {2, 4, 0, 6}
     * {3, 5, 6, 0}
     *
     * optimized matrix (without zeros):
     * {1, 2, 3}
     * {4, 5}
     * {6}
     * @param outputOptimizedData optimized data that is ready to calculate the distance matrix
     * @return the computed distance matrix that is ready to use in mods of Overwatch Workshop based on Path Node Builder
     */
    public int[][] calculate(Tuple<Vector[], int[][]> outputOptimizedData)
    {
        Vector[] outputPositions = outputOptimizedData.getObjectA();
        int[][] outputConnections = outputOptimizedData.getObjectB();
        int[][] distanceMatrix = new int[outputPositions.length][outputPositions.length];

        // Calculate all distances for each node
        for (int startNodeId = 0; startNodeId < distanceMatrix.length; startNodeId++)
        {
            Deque<Integer> queue = new ArrayDeque<>();
            Set<Integer> visitedNodes = new HashSet<>();

            // Add start node id in the queue and mark it as visited
            queue.addLast(startNodeId);
            visitedNodes.add(startNodeId);

            // Visit all nodes
            while (!queue.isEmpty())
            {
                // Poll first node from queue
                Integer currentNodeId = queue.pollFirst();

                // Visit neighbor nodes
                for (int endNodeId : outputConnections[currentNodeId])
                {
                    // Add neighbor node in queue and mark it as visited
                    if (!visitedNodes.contains(endNodeId))
                    {
                        queue.addLast(endNodeId);
                        visitedNodes.add(endNodeId);

                        // Accumulate distance to neighbor
                        distanceMatrix[startNodeId][endNodeId] = distanceMatrix[startNodeId][currentNodeId] + 1;
                    }
                }
            }
        }

        return distanceMatrix;
    }

    public int[][] removeLowerDiagonalFromDistanceMatrix(int[][] distanceMatrix)
    {
        int arraySize = distanceMatrix.length - 1;
        int[][] result = new int[arraySize][];

        // Don't copy the first 'zero' elements of inner arrays to the result
        for (int i = 0; i < arraySize; i++)
        {
            result[i] = new int[distanceMatrix[i].length - 1 - i];

            if (distanceMatrix[i].length - 1 >= 0)
                System.arraycopy(distanceMatrix[i], 1 + i, result[i], 0, distanceMatrix[i].length - 1 - i);
        }

        return result;
    }
}
