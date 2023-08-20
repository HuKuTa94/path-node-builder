package com.hukuta94.pathnodebuilder.calculator.filter.dto

import com.hukuta94.pathnodebuilder.common.type.Vector

data class DistanceMatrixDto(
    val positions: List<Vector>,
    val connections: List<IntArray>,
    val matrix: List<DoubleArray>
)
