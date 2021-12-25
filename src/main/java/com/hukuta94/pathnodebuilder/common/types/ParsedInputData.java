package com.hukuta94.pathnodebuilder.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ParsedInputData
{
    private Vector[] positions;
    private int[][] connections;
}
