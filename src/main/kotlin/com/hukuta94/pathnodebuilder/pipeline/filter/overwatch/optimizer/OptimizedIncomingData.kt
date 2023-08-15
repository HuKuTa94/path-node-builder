package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.optimizer

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector

data class OptimizedIncomingData(
    val builderNodePositions: List<Vector>,

    /**
     * 2D Array of integers (int[][])
     */
    val builderNodeConnections: List<IntArray>
)