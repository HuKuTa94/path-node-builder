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
        assertDoesNotThrow(()-> apiController.computeDistanceMatrix(inputData));
        assertEquals(HttpStatus.OK, apiController.computeDistanceMatrix(inputData).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix(null).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("BuilderNodePositions").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiController.computeDistanceMatrix("Global.BuilderNodePositions").getStatusCode());
    }
}
