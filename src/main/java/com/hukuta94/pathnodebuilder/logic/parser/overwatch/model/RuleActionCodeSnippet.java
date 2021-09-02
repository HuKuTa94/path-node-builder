package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents code snippet of actions for the workshop's rule of the following structure:
 * variables
 * {
 * 	global:
 * 		17: BuilderNodePositions
 * }
 *
 * actions
 * {
 * 	Global.BuilderNodePositions =
 * 		Array(
 * 			Vector(-16.004, 0.350, -15.965),
 * 			Vector(-15.994, 0.350, -0.043),
 * 			Vector(-15.981, 0.350, 15.908),
 * 		);
 * }
 */
public class RuleActionCodeSnippet
{
    /** Global vars that can be accessed from any rule (global or player) */
    private Map<Integer, Variable> global;

    public RuleActionCodeSnippet() {
        global = new HashMap<>();
    }

    public void addGlobal(Variable variable) {
        global.put(variable.getIndex(), variable);
    }

    public void addGlobals(Variable... variables) {
        for(Variable var : variables) {
            global.put(var.getIndex(), var);
        }
    }

    public Variable getGlobalByIndex(Integer index) {
        return global.get(index);
    }

    public Variable getGlobalByName(String variableName) {
        return global.values().stream()
                .filter(variable -> variable.getName().equals(variableName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString()
    {
        return variableBlockToString() + "\n\n" + actionBlockToString();
    }

    private String variableBlockToString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("variables\n{");

        if (!global.isEmpty())
        {
            builder.append("\n\tglobal:\n");
            appendVariables(builder, global);
        }

        builder.append("}");

        return builder.toString();
    }

    private void appendVariables(StringBuilder builder, Map<Integer, Variable> variables)
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

    private String actionBlockToString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("actions\n{\n");

        for(Variable var : global.values())
        {
            builder.append(var.toString());
            builder.append("\n");
        }

        builder.append("}");

        return builder.toString();
    }
}
