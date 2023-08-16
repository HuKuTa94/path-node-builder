package com.hukuta94.pathnodebuilder.pipeline.filter.calculator

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector

data class DistanceMatrixData(
    val positions: List<Vector>,
    val connections: List<IntArray>,
    val matrix: List<IntArray>
)
