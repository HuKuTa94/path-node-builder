package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

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

public class VariableBlock
{
    /**
     * Global vars that can be accessed from any rule (global or player)
     */
    private HashMap<Integer, Variable> global;

    /**
     * Player vars that can be accessed from the player only
     */
    private HashMap<Integer, Variable> player;

    public VariableBlock()
    {
        global = new HashMap<>();
        player = new HashMap<>();
    }

    /**
     * Adds new global variable
     * @param variable instance
     */
    public void addGlobal(Variable variable)
    {
        global.put(variable.getIndex(), variable);
    }

    /** Gets global var by index
     * @param index from 0 to 127
     */
    public Variable getGlobalByIndex(Integer index)
    {
        return global.get(index);
    }

    /**
     * Gets global var by variable name
     * @param variableName
     */
    public Variable getGlobalByName(String variableName)
    {
        return global.values().stream()
                .filter(variable -> variable.getName().equals(variableName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds new player variable
     * @param variable instance
     */
    public void addPlayer(Variable variable)
    {
        player.put(variable.getIndex(), variable);
    }

    /** Gets player var by index
     * @param index from 0 to 127
     * @return
     */
    public Variable getPlayerByIndex(Integer index)
    {
        return player.get(index);
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

    private void appendVariables(StringBuilder builder, HashMap<Integer, Variable> variables)
    {
        for (Map.Entry<Integer, Variable> entry: variables.entrySet())
        {
            builder.append("\t\t");
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue().getName());
            builder.append("\n");
        }
    }
}
