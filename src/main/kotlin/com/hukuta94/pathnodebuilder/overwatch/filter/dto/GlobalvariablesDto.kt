package com.hukuta94.pathnodebuilder.overwatch.filter.dto

import com.hukuta94.pathnodebuilder.common.type.Vector

data class GlobalvariablesDto(
    val builderNodePositions: List<Vector?>,

    /**
     * 2D Array of integers (int[][])
     */
    val builderNodeConnections: List<IntArray?>
)