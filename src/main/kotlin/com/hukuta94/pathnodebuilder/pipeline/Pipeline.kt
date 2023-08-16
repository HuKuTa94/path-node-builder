package com.hukuta94.pathnodebuilder.pipeline

import java.util.function.Function

interface Pipeline<IN, OUT> {
    fun execute(input: IN) : OUT

    val pipeline: Function<IN, OUT>
}