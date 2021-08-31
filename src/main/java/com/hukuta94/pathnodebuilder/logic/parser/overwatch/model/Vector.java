package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Describes vector type of Overwatch workshop
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vector
{
    private double x;
    private double y;
    private double z;

    @Override
    public String toString()
    {
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }
}
