package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ValidatorTest
{
    @Test
    @DisplayName("Index of var is out of allowed range")
    void badIndexTest()
    {
        // Then
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(-1, "varName"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(128, "varName"));
        assertDoesNotThrow(() -> Validator.validateVariable(0, "varName"));
        assertDoesNotThrow(() -> Validator.validateVariable(127, "varName"));
        assertDoesNotThrow(() -> Validator.validateVariable(1, "varName"));
        assertDoesNotThrow(() -> Validator.validateVariable(126, "varName"));
    }

    @Test
    @DisplayName("Variable name contains disallowed symbols")
    void badVariableNameTest()
    {
        // Then
        assertDoesNotThrow(() -> Validator.validateVariable(0, "Abc"));
        assertDoesNotThrow(() -> Validator.validateVariable(0, "_Abc"));
        assertDoesNotThrow(() -> Validator.validateVariable(0, "Abc123"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "1Abc"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Ab-c"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Abc%!^"));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Abc12ab%!"));
    }

    @Test
    @DisplayName("Variable name too long")
    void variableNameTooLongTest()
    {
        // Given
        String varName31Symbols = "qwertyuiopasdfghjklzxcvbnmqwe31";
        String varName32Symbols = "qwertyuiopasdfghjklzxcvbnmqwer32";
        String varName33Symbols = "qwertyuiopasdfghjklzxcvbnmqwert33";

        // Then
        assertDoesNotThrow(() -> Validator.validateVariable(0, varName31Symbols));
        assertDoesNotThrow(() -> Validator.validateVariable(127, varName32Symbols));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, varName33Symbols));
    }

    @Test
    @DisplayName("Variable name is not null")
    void variableNameIsNotNullTest()
    {
        // Then
        assertDoesNotThrow(() -> Validator.validateVariable(0, "varName"));
        assertThrows(NullPointerException.class, () -> Validator.validateVariable(0, null));
        assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, ""));
    }
}
