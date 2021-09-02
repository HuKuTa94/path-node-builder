package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.Constants;
import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.Variable;

import java.util.regex.Pattern;

public abstract class Validator
{
    private static final int INDEX_LOW = 0;
    private static final int INDEX_HIGH = 127;

    public static void validateVariable(Integer index, String variableName)
    {
        validateVariableIndex(index);
        validateNameField(variableName);
    }

    private static void validateVariableIndex(Integer index)
    {
        if (index < INDEX_LOW || index > INDEX_HIGH) {
            throw new IllegalArgumentException("Allowed range for index from 0 to 127 but got: " + index);
        }
    }

    private static void validateNameField(String nameField)
    {
        if (nameField == null) {
            throw new NullPointerException("Name can't be null");
        }

        if (nameField.length() == 0 || nameField.length() > Constants.NAME_FIELD_MAX_LENGTH) {
            throw new IllegalArgumentException("Name is empty or too long. Length: " + nameField.length());
        }

        if (!Pattern.matches(Constants.NAME_FIELD_REGEX, nameField)) {
            throw new IllegalArgumentException("Name has disallowed characters: " + nameField);
        }
    }
}
