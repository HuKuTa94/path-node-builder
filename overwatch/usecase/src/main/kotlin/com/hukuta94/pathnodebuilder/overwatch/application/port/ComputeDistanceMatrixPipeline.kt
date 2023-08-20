package com.hukuta94.pathnodebuilder.overwatch.application

import com.hukuta94.pathnodebuilder.calculator.filter.ComputeUnitDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.common.Pipeline
import com.hukuta94.pathnodebuilder.overwatch.application.port.`in`.ComputeDistanceMatrixUseCase
import com.hukuta94.pathnodebuilder.overwatch.filter.RemoveBottomSymmetricalPartOfMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.NormalizerFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.IncomingSnippetParserFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.OutgoingSnippetParserFilter
import java.util.function.Function

class ComputeDistanceMatrixPipeline constructor(
    incomingSnippetParserFilter: IncomingSnippetParserFilter,
    normalizerFilter: NormalizerFilter,
    computeUnitDistanceMatrixFilter: ComputeUnitDistanceMatrixFilter,
    processUniDirectionNodesFilter: ProcessUniDirectionNodesFilter,
    removeBottomSymmetricalPartOfMatrixFilter: RemoveBottomSymmetricalPartOfMatrixFilter,
    outgoingSnippetParserFilter: OutgoingSnippetParserFilter
) : Pipeline<String, String>, ComputeDistanceMatrixUseCase {

    override val pipeline: Function<String, String> =
        incomingSnippetParserFilter
            .andThen(normalizerFilter)
            .andThen(computeUnitDistanceMatrixFilter)
            .andThen(processUniDirectionNodesFilter)
            .andThen(removeBottomSymmetricalPartOfMatrixFilter)
            .andThen(outgoingSnippetParserFilter)

    override fun execute(input: String): String {
        return pipeline.apply(input)
    }
}
