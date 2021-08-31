package com.hukuta94.pathnodebuilder.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Distance matrix of the unit-edge graph
 */
public class DistanceMatrix
{
    // Protected fields for unit-tests
    protected int[][] matrix;

    /**
     * true - fill upper part of the left diagonal only (matrix[x][y]). It can reduce used memory and improve performance.
     * false - fill the full matrix. The lower part of the left diagonal will have inverted values (matrix[y][x])
     */
    @Getter
    private boolean isOptimized;

    /**
     * Creates an instance of greedy not optimized distance matrix
     * @param size of the square matrix
     */
    public DistanceMatrix(int size)
    {
        initMatrix(size, false);
    }

    /**
     * Creates an instance of lazy, optimized distance matrix
     * @param size of the the first array
     * @param optimized true - fill upper part of the left diagonal only (matrix[x][y]). It can reduce used memory
     *                  and improve performance. false - fill the full matrix. The lower part of the left diagonal
     *                  will have inverted values (matrix[y][x])
     */
    public DistanceMatrix(int size, boolean optimized)
    {
        initMatrix(size, optimized);
    }

    private void initMatrix(int size, boolean optimized)
    {
        isOptimized = optimized;

        if (!isOptimized)
        {
            matrix = new int[size][size];
            return;
        }

        matrix = new int[size][];
        int x = 0;
        for (int y = size; y > 0; y--)
        {
            matrix[x] = new int[y];
            x++;
        }
    }

    public int getValue(int x, int y)
    {
        return matrix[x][y];
    }

    public void setValue(int x, int y, int value)
    {
        matrix[x][y] = value;
    }
}
