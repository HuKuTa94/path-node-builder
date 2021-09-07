package com.hukuta94.pathnodebuilder.service;

import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.logic.DistanceMatrixCalculator;
import com.hukuta94.pathnodebuilder.logic.Optimizator;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.OverwatchParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistanceMatrixService
{
    private final OverwatchParser overwatchParser;
    private final Optimizator optimizator;
    private final DistanceMatrixCalculator distanceMatrixCalculator;

    public String computeDistanceMatrix(String inputString, boolean optimized) throws Exception
    {
        // Convert input data of Overwatch Workshop to vector and connection arrays
        Tuple<Vector[], int[][]> inputData = overwatchParser.parseInputData(inputString);

        // Optimize converted input data, delete all 'null' elements that were deleted in Workshop
        Tuple<Vector[], int[][]>  optimizedData = optimizator.optimizeInputData(inputData, true);

        // Calculate distance matrix using optimized data
        int[][] distanceMatrix = distanceMatrixCalculator.calculate(optimizedData, optimized);

        // Convert result to the Overwatch Workshop format
        return overwatchParser.parseOutputData(
            optimizedData.getObjectA(),
            optimizedData.getObjectB(),
            distanceMatrix);
    }
}
