package com.hukuta94.pathnodebuilder.logic.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class ParserHelperTest
{
    @Test
    @DisplayName("Load test file")
    void loadTestFile() throws Exception
    {
        // Given
        String expectedContent = "test content of test file\n";

        // When
        String actualContent = ParserHelper.loadTestFile("", "testFile");

        // Then
        Assertions.assertEquals(expectedContent, actualContent);
    }
}
