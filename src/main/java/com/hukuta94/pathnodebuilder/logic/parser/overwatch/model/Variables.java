package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.Validator;

import java.util.Map;
import java.util.HashMap;

/**
 * Describes variable names (global, player) in following the structure:
 * variables
 * {
 * 	global:
 * 		0: VarName1
 * 		1: VarName2
 *
 * 	player:
 * 		0: VarName1
 * 		1: VarName2
 * }
 */

public class Variables
{
    /**
     * Global vars that can be accessed from any rule (global or player)
     */
    private HashMap<Integer, String> global;

    /**
     * Player vars that can be accessed from the player only
     */
    private HashMap<Integer, String> player;

    public Variables()
    {
        global = new HashMap<>();
        player = new HashMap<>();
    }

    /**
     * Adds new global variable at index
     * @param index from 0 to 127
     */
    public void addGlobal(Integer index, String variableName)
    {
        Validator.validateVariable(index, variableName);
        global.put(index, variableName);
    }

    /**
     * Adds new player variable at index
     * @param index from 0 to 127
     */
    public void addPlayer(Integer index, String variableName)
    {
        Validator.validateVariable(index, variableName);
        player.put(index, variableName);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("variables\n{");

        if (!global.isEmpty())
        {
            builder.append("\n\tglobal:\n");
            appendVariables(builder, global);
        }

        if (!player.isEmpty())
        {
            builder.append("\n\tplayer:\n");
            appendVariables(builder, player);
        }

        builder.append("}");

        return builder.toString();
    }

    private void appendVariables(StringBuilder builder, HashMap<Integer, String> variables)
    {
        for (Map.Entry<Integer, String> entry: variables.entrySet())
        {
            builder.append("\t\t");
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());
            builder.append("\n");
        }
    }
}
