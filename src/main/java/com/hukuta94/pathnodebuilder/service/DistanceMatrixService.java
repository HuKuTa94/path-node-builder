package com.hukuta94.pathnodebuilder.service;

import com.hukuta94.pathnodebuilder.common.types.ParsedInputData;
import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.logic.DistanceMatrixCalculator;
import com.hukuta94.pathnodebuilder.logic.Optimizator;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.OverwatchParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DistanceMatrixService
{
    private final OverwatchParser overwatchParser;
    private final Optimizator optimizator;
    private final DistanceMatrixCalculator distanceMatrixCalculator;

    public String computeDistanceMatrix(String inputString) throws Exception
    {
        // Convert input data of Overwatch Workshop to vector and connection arrays
        ParsedInputData inputData = overwatchParser.parseInputData(inputString);

        // Optimize converted input data, delete all 'null' elements that were deleted in Workshop
        Tuple<Vector[], int[][]>  optimizedData = optimizator.optimizeInputData(inputData);

        // Calculate distance matrix using optimized data
        int[][] distanceMatrix = distanceMatrixCalculator.calculate(optimizedData);
        Map<Integer, List<List<Integer>>> processedUniDirectionNodesMatrix = distanceMatrixCalculator.processUniDirectionNodes(distanceMatrix);
        Map<Integer, List<List<Integer>>> distanceMatrixWithoutZeroElements =
                distanceMatrixCalculator.removeLowerDiagonalFromDistanceMatrix(processedUniDirectionNodesMatrix);

        // Convert result to the Overwatch Workshop format
        return overwatchParser.parseOutputData(
            optimizedData.getObjectA(),
            optimizedData.getObjectB(),
            distanceMatrixWithoutZeroElements);
    }
}
