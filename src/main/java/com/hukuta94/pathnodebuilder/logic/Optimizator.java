package com.hukuta94.pathnodebuilder.logic;

import com.hukuta94.pathnodebuilder.common.types.ParsedInputData;
import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import org.springframework.stereotype.Service;

/**
 * Finds and deletes nullable elements in input data (position array and connection array). When a user uses
 * the Path Node Builder mod of Overwatch Workshop, he can delete nodes. It produces null elements called 'holes'.
 * The class scans input data and optimizes it, sets new indexes for elements of the position array.
 * The case with a connection array is a little bit harder. It requires to recompute and update all indexes
 * for each element if there are nullable elements (holes) between them.
 */
@Service
public class Optimizator
{
    public Tuple<Vector[], int[][]> optimizeInputData(ParsedInputData inputData)
    {
        Vector[] inputPositions = inputData.getPositions();
        int[][] inputConnections = inputData.getConnections();

        Vector[] outputPositions = new Vector[inputPositions.length];
        int[][] outputConnections = new int[inputConnections.length][];

        int emptySpaceCount = 0;
        int oldElementIndex;
        int newElementIndex;

        // Begin optimization input data
        for (int i = 0; i < inputPositions.length; i++)
        {
            // Count of 'null' elements between side elements (also skips the nodes without any connections)
            if (inputConnections[i] == null) {
                emptySpaceCount++;
                continue;
            }

            // Save 'old' index to update connection arrays
            oldElementIndex = i;

            // Calculate index for a new element if we have empty spaces between side elements
            newElementIndex = emptySpaceCount == 0 ? i : i - emptySpaceCount;

            // Copy values from input to output array
            outputPositions[newElementIndex] = inputPositions[oldElementIndex];
            outputConnections[newElementIndex] = inputConnections[oldElementIndex];

            // Go to next iteration if we don't have spaces between side elements
            if (emptySpaceCount == 0 && outputConnections[0] != null) {
                continue;
            }

            // Update indexes in result output connection array in range from 0 to current iterator (i-1)
            for (int j = 0; j < i - 1; j++)
            {
                // Skip nullable element
                if (outputConnections[j] == null) {
                    continue;
                }

                // Iterate inner output array of node connections
                for (int k = 0; k < outputConnections[j].length; k++)
                {
                    // Update old index to new
                    if (outputConnections[j][k] == oldElementIndex) {
                        outputConnections[j][k] = newElementIndex;
                    }
                }
            }

            // Update next indexes in input connection array from current iterator (i+1) position
            for (int j = i + 1; j < inputConnections.length; j++)
            {
                // Skip nullable element
                if (inputConnections[j] == null) {
                    continue;
                }

                // Iterate inner input array of node connections
                for (int k = 0; k < inputConnections[j].length; k++)
                {
                    // Update old index to new
                    if (inputConnections[j][k] == oldElementIndex) {
                        inputConnections[j][k] = newElementIndex;
                    }
                }
            }
        }

        return compressArrays(outputPositions, outputConnections);
    }

    private Tuple<Vector[], int[][]> compressArrays(Vector[] positionArray, int[][] connectionArray)
    {
        int countOfNullableItems = 0;
        for (int i = positionArray.length - 1; i > 0; i--)
        {
            if (positionArray[i] == null) {
                countOfNullableItems++;
            } else {
                break;
            }
        }

        // Create new compressed arrays
        Vector[] resultPositionArray = new Vector[positionArray.length - countOfNullableItems];
        int[][] resultConnectionArray = new int[positionArray.length - countOfNullableItems][];

        for (int i = 0; i < resultPositionArray.length; i++)
        {
            resultPositionArray[i] = positionArray[i];
            resultConnectionArray[i] = connectionArray[i];
        }

        return new Tuple<>(resultPositionArray, resultConnectionArray);
    }
}
