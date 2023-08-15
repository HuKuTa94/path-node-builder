package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector

data class ParsedIncomingData(
    val builderNodePositions: List<Vector?>,

    /**
     * 2D Array of integers (int[][])
     */
    val builderNodeConnections: List<List<Int>?>
)