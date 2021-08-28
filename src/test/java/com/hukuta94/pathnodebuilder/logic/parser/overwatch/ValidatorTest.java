package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class ValidatorTest
{
    @Test
    @DisplayName("Index of var is out of allowed range")
    void badIndexTest()
    {
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(-1, "varName"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(128, "varName"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, "varName"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(127, "varName"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(1, "varName"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(126, "varName"));
    }

    @Test
    @DisplayName("Variable name contains disallowed symbols")
    void badVariableNameTest()
    {
        // Then
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, "Abc"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, "_Abc"));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, "Abc123"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "1Abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Ab-c"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Abc%!^"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, "Abc12ab%!"));
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
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, varName31Symbols));
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(127, varName32Symbols));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, varName33Symbols));
    }

    @Test
    @DisplayName("Variable name is not null")
    void variableNameIsNotNullTest()
    {
        // Then
        Assertions.assertDoesNotThrow(() -> Validator.validateVariable(0, "varName"));
        Assertions.assertThrows(NullPointerException.class, () -> Validator.validateVariable(0, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validateVariable(0, ""));
    }
}
