package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

/**
 * Describes list of actions for the rule in following structure:
 * actions
 * {
 * 	Global.WorkshopSettingInstructions = False;
 * 	Global.PropertyFreeCamera = True;
 * }
 */
@AllArgsConstructor
public class Actions
{
    @Getter
    private List<Variable> list;

    public Actions() {
        list = new ArrayList<>();
    }

    public Actions(int initialCapacity) {
        list = new ArrayList<>(3);
    }

    public void add(Variable var) {
        list.add(var);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("actions\n{\n");

        for(Variable var : list)
        {
            builder.append(var.toString());
            builder.append("\n");
        }

        builder.append("}");

        return builder.toString();
    }
}
