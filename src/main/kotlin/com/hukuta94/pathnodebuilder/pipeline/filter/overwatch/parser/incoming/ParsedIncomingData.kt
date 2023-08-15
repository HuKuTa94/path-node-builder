package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector

data class ParsedIncomingData(
    val builderNodePositions: List<Vector?>,
    val builderNodeConnections: List<IntArray>
)