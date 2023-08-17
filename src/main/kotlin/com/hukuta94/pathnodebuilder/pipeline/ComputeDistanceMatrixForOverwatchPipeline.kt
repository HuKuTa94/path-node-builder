package com.hukuta94.pathnodebuilder.pipeline

import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.ComputeFullDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.RemoveLowerMatrixDiagonalFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OptimizerFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.IncomingParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OutgoingParserFilter
import java.util.function.Function

class ComputeDistanceMatrixForOverwatchPipeline constructor(
    incomingParserFilter: IncomingParserFilter,
    optimizerFilter: OptimizerFilter,
    computeFullDistanceMatrixFilter: ComputeFullDistanceMatrixFilter,
    processUniDirectionNodesFilter: ProcessUniDirectionNodesFilter,
    removeLowerMatrixDiagonalFilter: RemoveLowerMatrixDiagonalFilter,
    outgoingParserFilter: OutgoingParserFilter
) : Pipeline<String, String> {

    override val pipeline: Function<String, String> =
        incomingParserFilter
            .andThen(optimizerFilter)
            .andThen(computeFullDistanceMatrixFilter)
            .andThen(processUniDirectionNodesFilter)
            .andThen(removeLowerMatrixDiagonalFilter)
            .andThen(outgoingParserFilter)

    override fun execute(input: String): String {
        return pipeline.apply(input)
    }
}
