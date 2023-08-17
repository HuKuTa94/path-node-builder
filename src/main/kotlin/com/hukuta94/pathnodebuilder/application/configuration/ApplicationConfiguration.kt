package com.hukuta94.pathnodebuilder.application.configuration

import com.hukuta94.pathnodebuilder.adapter.web.PresentationController
import com.hukuta94.pathnodebuilder.adapter.web.RestController
import com.hukuta94.pathnodebuilder.pipeline.ComputeDistanceMatrixForOverwatchPipeline
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.ComputeFullDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.RemoveLowerMatrixDiagonalFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.NormalizerFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.IncomingSnippetParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OutgoingSnippetParserFilter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
open class ApplicationConfiguration {
    @Bean
    open fun incomingSnippetParserFilter() = IncomingSnippetParserFilter()

    @Bean
    open fun normalizerFilter() = NormalizerFilter()

    @Bean
    open fun computeFullDistanceMatrixFilter() = ComputeFullDistanceMatrixFilter()

    @Bean
    open fun processUniDirectionNodesFilter() = ProcessUniDirectionNodesFilter()

    @Bean
    open fun removeLowerMatrixDiagonalFilter() = RemoveLowerMatrixDiagonalFilter()

    @Bean
    open fun outgoingSnippetParserFilter() = OutgoingSnippetParserFilter()

    @Bean
    open fun computeDistanceMatrixForOverwatchPipeline() = ComputeDistanceMatrixForOverwatchPipeline(
        incomingSnippetParserFilter = incomingSnippetParserFilter(),
        normalizerFilter = normalizerFilter(),
        computeFullDistanceMatrixFilter = computeFullDistanceMatrixFilter(),
        processUniDirectionNodesFilter = processUniDirectionNodesFilter(),
        removeLowerMatrixDiagonalFilter = removeLowerMatrixDiagonalFilter(),
        outgoingSnippetParserFilter = outgoingSnippetParserFilter()
    )

    @Bean
    open fun restController() = RestController(computeDistanceMatrixForOverwatchPipeline())

    @Bean
    open fun presentationController() = PresentationController()
}