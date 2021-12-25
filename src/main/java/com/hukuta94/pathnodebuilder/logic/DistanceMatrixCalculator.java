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

    @Deprecated
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

    public Map<Integer, List<List<Integer>>> removeLowerDiagonalFromDistanceMatrix(Map<Integer, List<List<Integer>>> distanceMatrix)
    {
        int matrixSize = distanceMatrix.size() - 1;
        Map<Integer, List<List<Integer>>> result = new HashMap<>(matrixSize);

        // Don't copy the first 'zero' elements of inner arrays to the result
        for (int i = 0; i < matrixSize; i++)
        {
            if (distanceMatrix.get(i).size() - 1 >= 0)
            {
                int distanceListSize = distanceMatrix.get(i).size() - i;
                List<List<Integer>> newDistanceList = new ArrayList<>(distanceListSize);
                for (int j = 1 + i; j < distanceListSize; j++)
                {
                    newDistanceList.add(distanceMatrix.get(i).get(j));
                }

                result.put(i, newDistanceList);
            }
        }

        return result;
    }

    public Map<Integer, List<List<Integer>>> processUniDirectionNodes(int[][] distanceMatrix)
    {
        int matrixSize = distanceMatrix.length;
        Map<Integer, List<List<Integer>>> processedDistanceMatrix = new HashMap<>(matrixSize);

        for (int row = 0; row < matrixSize; row++)
        {
            // Uni-direction nodes can has two values of distance
            // Example:
            //  from node A to node B distance = 1, node A are connected to node B
            //  from node B to node A distance = 4, node B aren't connected to node A
            List<List<Integer>> nodeDistances = new ArrayList<>();

            for (int column = 0; column < matrixSize; column++)
            {
                int highDiagonalDistanceValue = distanceMatrix[row][column];
                int lowDiagonalDistanceValue = distanceMatrix[column][row];

                // Bi-direction node
                if (highDiagonalDistanceValue == lowDiagonalDistanceValue)
                {
                    nodeDistances.add(Collections.singletonList(highDiagonalDistanceValue));
                }
                // Uni-direction node
                else
                {
                    nodeDistances.add(Arrays.asList(highDiagonalDistanceValue, lowDiagonalDistanceValue));
                }
            }
            processedDistanceMatrix.put(row, nodeDistances);
        }

        return processedDistanceMatrix;
    }
}
