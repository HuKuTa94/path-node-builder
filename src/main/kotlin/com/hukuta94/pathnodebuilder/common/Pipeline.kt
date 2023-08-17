package com.hukuta94.pathnodebuilder.common

import java.util.function.Function

interface Pipeline<IN, OUT> {
    val pipeline: Function<IN, OUT>
}