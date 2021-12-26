package com.hukuta94.pathnodebuilder.service;

import com.hukuta94.pathnodebuilder.common.types.Tuple;
import com.hukuta94.pathnodebuilder.common.types.Vector;
import com.hukuta94.pathnodebuilder.logic.DistanceMatrixCalculator;
import com.hukuta94.pathnodebuilder.logic.Optimizator;
import com.hukuta94.pathnodebuilder.logic.parser.ParserHelper;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.OverwatchParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DistanceMatrixServiceTest
{
    @Autowired
    OverwatchParser overwatchParser;

    @Autowired
    Optimizator optimizator;

    @Autowired
    DistanceMatrixCalculator distanceMatrixCalculator;

    @Test
    @DisplayName("Data with uni-direction nodes")
    void dataWithUniDirectionNodesTest() throws Exception
    {
        // Given:
        String inputData = ParserHelper.loadTestFile("overwatch/4/before/", null);
        String expectedResult = ParserHelper.loadTestFile("overwatch/4/after/", null);

        // When
        Tuple<Vector[], int[][]> parsedInputData = overwatchParser.parseInputData(inputData);

        Tuple<Vector[], int[][]> optimizedData = optimizator.optimizeInputData(parsedInputData);

        int[][] calculatedDistanceMatrix = distanceMatrixCalculator.calculateFullMatrix(optimizedData);

        float[][] processedUniDirectionDistanceMatrix =
                distanceMatrixCalculator.processUniDirectionNodes(calculatedDistanceMatrix);

        float[][] distanceMatrixWithoutLowerDiagonal =
                distanceMatrixCalculator.removeLowerDiagonalFromDistanceMatrix(processedUniDirectionDistanceMatrix);

        String actualResult = overwatchParser.parseOutputData(
                optimizedData.getObjectA(),
                optimizedData.getObjectB(),
                distanceMatrixWithoutLowerDiagonal);

        // Then
        assertEquals(expectedResult, actualResult);
    }
}
