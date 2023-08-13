package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Calculator of distance matrix of the unit-edge graph
 */
@Service
public class DistanceMatrixCalculator
{
    /**
     * Calculates distance matrix from output optimized data (position array and connection array).
     * default matrix (full):
     * with zeros and possible duplicates in lower diagonal
     * {0, 1, 2, 3}
     * {1, 0, 4, 5}
     * {4, 4, 0, 6}
     * {3, 5, 6, 0}
     *
     * optimized matrix (half):
     * only higher diagonal without zeros,
     * bi-direction nodes has float distance value
     * Example 2.4: distance from node A to B equals 2, distance from node B to A equals 4.
     * {1, 2.4, 3}
     * {4, 5}
     * {6}
     * @param outputOptimizedData optimized data that is ready to calculate the distance matrix
     * @return the computed distance matrix that is ready to use in mods of Overwatch Workshop based on Path Node Builder
     */
    public int[][] calculateFullMatrix(Tuple<Vector[], int[][]> outputOptimizedData)
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

    public float[][] removeLowerDiagonalFromDistanceMatrix(float[][] distanceMatrix)
    {
        int arraySize = distanceMatrix.length - 1;
        float[][] result = new float[arraySize][];

        for (int i = 0; i < arraySize; i++)
        {
            // Don't copy the first and 'zero' elements of inner arrays to the result
            result[i] = new float[distanceMatrix[i].length - 1 - i];

            if (distanceMatrix[i].length - 1 >= 0)
                System.arraycopy(distanceMatrix[i], 1 + i, result[i], 0, distanceMatrix[i].length - 1 - i);
        }

        return result;
    }

    /**
     * Uni-direction nodes can have two values of distance. The method collapse these values into one float number
     * Example: number 1 and number 4 will be collapsed to float 1.4
     * It means that:
     * distance from node A to node B equals 1, node A are connected to node B
     * distance from node B to node A equals 4, node B aren't connected to node A
     * @param distanceMatrix - full computed matrix
     */
    public float[][] processUniDirectionNodes(int[][] distanceMatrix)
    {
        int matrixSize = distanceMatrix.length;
        float[][] resultMatrix = new float[matrixSize][matrixSize];

        for (int row = 0; row < matrixSize; row++)
        {
            for (int column = 0; column < matrixSize; column++)
            {
                int highDiagonalValue = distanceMatrix[row][column];
                int lowDiagonalValue = distanceMatrix[column][row];

                resultMatrix[row][column] = collapseIntegerNumbersToFloat(highDiagonalValue, lowDiagonalValue);
            }
        }

        return resultMatrix;
    }

    /**
     * Collapses two integer numbers to float number.
     * @param leftNumber - for example 1
     * @param rightNumber - for example 4
     * @return float number 1.004
     */
    private float collapseIntegerNumbersToFloat(int leftNumber, int rightNumber)
    {
        return leftNumber + rightNumber / 1000f;
    }
}
