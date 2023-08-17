package com.hukuta94.pathnodebuilder.pipeline

import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.ComputeFullDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.RemoveLowerMatrixDiagonalFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.NormalizerFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.IncomingSnippetParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OutgoingSnippetParserFilter
import java.util.function.Function

class ComputeDistanceMatrixForOverwatchPipeline constructor(
    incomingSnippetParserFilter: IncomingSnippetParserFilter,
    normalizerFilter: NormalizerFilter,
    computeFullDistanceMatrixFilter: ComputeFullDistanceMatrixFilter,
    processUniDirectionNodesFilter: ProcessUniDirectionNodesFilter,
    removeLowerMatrixDiagonalFilter: RemoveLowerMatrixDiagonalFilter,
    outgoingSnippetParserFilter: OutgoingSnippetParserFilter
) : Pipeline<String, String> {

    override val pipeline: Function<String, String> =
        incomingSnippetParserFilter
            .andThen(normalizerFilter)
            .andThen(computeFullDistanceMatrixFilter)
            .andThen(processUniDirectionNodesFilter)
            .andThen(removeLowerMatrixDiagonalFilter)
            .andThen(outgoingSnippetParserFilter)

    override fun execute(input: String): String {
        return pipeline.apply(input)
    }
}
