package com.hukuta94.pathnodebuilder.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * The basic class that describes a simple vector with a 3 coords
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
}
