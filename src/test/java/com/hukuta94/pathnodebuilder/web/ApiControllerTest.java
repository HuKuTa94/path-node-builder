package com.hukuta94.pathnodebuilder.web;

import com.hukuta94.pathnodebuilder.logic.parser.ParserHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApiControllerTest
{
    @Autowired
    ApiController apiController;

    @Test
    @DisplayName("Successfully /api/distance-matrix/ method")
    void distanceMatrixMethodTest() throws Exception
    {
        // Given:
        String inputData = ParserHelper.loadTestFile("overwatch/1/", null);

        // Then:
        assertDoesNotThrow(()-> apiController.computeDistanceMatrix(inputData, true));
        assertDoesNotThrow(()-> apiController.computeDistanceMatrix(inputData, false));
        assertEquals(HttpStatus.OK, apiController.computeDistanceMatrix(inputData, true).getStatusCode());
        assertEquals(HttpStatus.OK, apiController.computeDistanceMatrix(inputData, false).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix(null, true).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("", true).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("BuilderNodePositions", true).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("BuilderNodePositions", true).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("Global.BuilderNodePositions", true).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("Global.BuilderNodePositions", true).getStatusCode());
    }
}
