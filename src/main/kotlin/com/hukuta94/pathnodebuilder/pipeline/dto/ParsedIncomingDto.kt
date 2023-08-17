package com.hukuta94.pathnodebuilder.pipeline.dto

import com.hukuta94.pathnodebuilder.pipeline.common.Vector

data class ParsedIncomingDto(
    val builderNodePositions: List<Vector?>,

    /**
     * 2D Array of integers (int[][])
     */
    val builderNodeConnections: List<IntArray?>
)