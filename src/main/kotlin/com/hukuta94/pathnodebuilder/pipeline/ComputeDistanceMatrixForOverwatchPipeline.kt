package com.hukuta94.pathnodebuilder.pipeline

import com.hukuta94.pathnodebuilder.pipeline.filter.calculator.ComputeFullDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.calculator.RemoveLowerMatrixDiagonalFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.optimizer.OptimizerFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming.IncomingParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.outgoing.OutgoingParserFilter
import java.util.function.Function

class ComputeDistanceMatrixForOverwatchPipeline : Pipeline<String, String> {

    override val pipeline: Function<String, String> =
        IncomingParserFilter()
            .andThen(OptimizerFilter())
            .andThen(ComputeFullDistanceMatrixFilter())
            .andThen(ProcessUniDirectionNodesFilter())
            .andThen(RemoveLowerMatrixDiagonalFilter())
            .andThen(OutgoingParserFilter())

    override fun execute(input: String): String {
        return pipeline.apply(input)
    }
}

