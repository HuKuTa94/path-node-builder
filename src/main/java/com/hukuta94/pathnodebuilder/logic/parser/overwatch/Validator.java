package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.model.Constants;

public abstract class Validator
{
    public static void validateVariable(Integer index, String variableName)
    {
        validateVariableIndex(index);
        validateVariableName(variableName);
    }

    private static void validateVariableIndex(Integer index)
    {
        if (index < 0 || index > 127) {
            throw new IllegalArgumentException("Allowed range for index from 0 to 127 but got: " + index);
        }
    }

    private static void validateVariableName(String variableName)
    {
        if (variableName == null) {
            throw new NullPointerException("Variable name can't be null");
        }

        if (variableName.length() == 0 || variableName.length() > Constants.NAME_FIELD_MAX_LENGTH) {
            throw new IllegalArgumentException("Variable name is empty or too long. Length: " + variableName.length());
        }
    }
}
