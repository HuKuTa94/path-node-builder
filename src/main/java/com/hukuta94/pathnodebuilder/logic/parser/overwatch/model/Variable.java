package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import com.hukuta94.pathnodebuilder.logic.parser.overwatch.Validator;
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
    private Integer index;
    private String name;
    private T value;

    public Variable(Integer index, String name)
    {
        Validator.validateVariable(index, name);
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        // Parse as single var (variable block)
        builder.append("\tGlobal.");
        builder.append(name);

        // Parse as var with value (action block)
        if (value != null)
        {
            builder.append(" = \n\t\t");
            builder.append(value.toString());
        }
        return builder.toString();
    }
}
