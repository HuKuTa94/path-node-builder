package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.common.types.Tuple;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Calculator of distance matrix of the unit-edge graph
 */
@Component
public class DistanceMatrixCalculator
{
    /**
     * Creates an instance of default or optimized distance matrix.
     * default matrix result (greedy algorithm):
     * {0, 1, 2, 3}
     * {1, 0, 4, 5}
     * {2, 4, 0, 6}
     * {3, 5, 6, 0}
     *
     * optimized matrix result (lazy algorithm):
     * {0, 1, 2, 3}
     * {0, 4, 5}
     * {0, 6}
     *
     * @param size of the the first array
     * @param optimized true - fill upper part of the main diagonal only (matrix[x][y]). It can reduce used memory
     *                  and improve performance. false - fill the full matrix. The lower part of the main diagonal
     *                  will have inverted values (matrix[y][x])
     */
    private int[][] createEmptyDistanceMatrix(int size, boolean optimized)
    {
        if (!optimized)
        {
            return new int[size][size];
        }

        int[][] distanceMatrix = new int[size - 1][];
        int x = 0;
        for (int y = size; y > 1; y--)
        {
            distanceMatrix[x] = new int[y];
            x++;
        }

        return distanceMatrix;
    }

    /**
     * Calculates distance matrix from output optimized data (position array and connection array)
     * @param outputOptimizedData optimized data that is ready to calculate the distance matrix
     * @param optimized true - fill upper part of the left diagonal only (matrix[x][y]). It can reduce used memory
     *                  and improve performance. false - fill the full matrix. The lower part of the main diagonal
     *                  will have inverted values (matrix[y][x])
     * @return the computed distance matrix that is ready to use in mods of Overwatch Workshop based on Path Node Builder
     */
    public int[][] calculate(Tuple<Vector[], int[][]> outputOptimizedData, boolean optimized)
    {
        Vector[] outputPositions = outputOptimizedData.getObjectA();
        int[][] outputConnections = outputOptimizedData.getObjectB();
        int[][] distanceMatrix = createEmptyDistanceMatrix(outputPositions.length, optimized);

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

                        // Skip element if it is lower than the main diagonal
                        if (optimized && endNodeId <= startNodeId) {
                            continue;
                        }

                        // Accumulate distance to neighbor
                        // Default matrix algorithm (gready)
                        if (!optimized) {
                            distanceMatrix[startNodeId][endNodeId] = distanceMatrix[startNodeId][currentNodeId] + 1;
                            continue;
                        }

                        // Optimized matrix algorithm (lazy)
                        // End node is lower than the main diagonal
                        if (startNodeId > endNodeId) {
                            distanceMatrix[startNodeId][endNodeId - startNodeId] = distanceMatrix[startNodeId][currentNodeId - endNodeId] + 1;
                        }

                        // End node is higher than the main diagonal
                        else {
                            int currentDistance;

                            // Current node has been visited previously (it is lower than the main diagonal)
                            if (currentNodeId < startNodeId) {
                                currentDistance = distanceMatrix[currentNodeId][startNodeId - currentNodeId];
                            }
                            // Current node has not been visited before (it is higher than the main diagonal)
                            else {
                                currentDistance = distanceMatrix[startNodeId][currentNodeId - startNodeId];
                            }

                            distanceMatrix[startNodeId][endNodeId - startNodeId] = currentDistance + 1;
                        }
                    }
                }
            }
        }

        return distanceMatrix;
    }
}
