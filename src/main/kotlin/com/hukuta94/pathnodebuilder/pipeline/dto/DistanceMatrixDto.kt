package com.hukuta94.pathnodebuilder.pipeline.dto

import com.hukuta94.pathnodebuilder.pipeline.common.Vector

data class DistanceMatrixDto(
    val positions: List<Vector>,
    val connections: List<IntArray>,
    val matrix: List<DoubleArray>
)
