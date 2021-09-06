package com.hukuta94.pathnodebuilder.logic.parser.overwatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator
{
    public static final int NAME_FIELD_MAX_LENGTH = 32;
    public static final String NAME_FIELD_REGEX = "^([a-zA-Z_][a-zA-Z0-9_]+)$";

    private static final int INDEX_LOW = 0;
    private static final int INDEX_HIGH = 127;

    private static String INPUT_VAR_POSITIONS_NAME;
    private static String INPUT_VAR_CONNECTIONS_NAME;
    private static Pattern PATTERN_INPUT_VARS_EXIST;

    public Validator(@Value("${overwatch.variables.input.positions-name}") String inputVarPositionsName,
                     @Value("${overwatch.variables.input.connections-name}") String inputVarConnectionsName)
    {
        INPUT_VAR_POSITIONS_NAME = inputVarPositionsName;
        INPUT_VAR_CONNECTIONS_NAME = inputVarConnectionsName;

        PATTERN_INPUT_VARS_EXIST = Pattern.compile(
            String.format("(Global\\.%s)|(Global\\.%s)",
                    INPUT_VAR_POSITIONS_NAME,
                    INPUT_VAR_CONNECTIONS_NAME));
    }

    public void validateInputString(String inputString) throws Exception
    {
        Matcher matcher = PATTERN_INPUT_VARS_EXIST.matcher(inputString);
        if (!matcher.find())
        {
            throw new Exception("Input data does not contain variables '" +
                    INPUT_VAR_POSITIONS_NAME + "' and/or '" + INPUT_VAR_CONNECTIONS_NAME + "'");
        }
    }

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

        if (nameField.length() == 0 || nameField.length() > NAME_FIELD_MAX_LENGTH) {
            throw new IllegalArgumentException("Name is empty or too long. Length: " + nameField.length());
        }

        if (!Pattern.matches(NAME_FIELD_REGEX, nameField)) {
            throw new IllegalArgumentException("Name has disallowed characters: " + nameField);
        }
    }
}
