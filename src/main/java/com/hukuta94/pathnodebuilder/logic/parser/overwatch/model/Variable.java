package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Describes variable action for the rule (only global var)
 * Example:
 * Global.WorkshopSettingInstructions = False;
 */
@Getter
@Setter
@AllArgsConstructor
public class Variable<T>
{
    private String name;
    private T value;

    @Override
    public String toString()
    {
        return "\tGlobal." + name + " = \n\t\t" + value.toString();
    }
}
