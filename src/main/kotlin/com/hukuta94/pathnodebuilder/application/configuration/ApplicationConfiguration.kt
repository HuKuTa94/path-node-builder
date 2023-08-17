package com.hukuta94.pathnodebuilder.application.configuration

import com.hukuta94.pathnodebuilder.adapter.web.PresentationController
import com.hukuta94.pathnodebuilder.adapter.web.RestController
import com.hukuta94.pathnodebuilder.pipeline.ComputeDistanceMatrixForOverwatchPipeline
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.ComputeFullDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.calculator.RemoveLowerMatrixDiagonalFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OptimizerFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.IncomingParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OutgoingParserFilter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
open class ApplicationConfiguration {
    @Bean
    open fun incomingParserFilter() = IncomingParserFilter()

    @Bean
    open fun optimizerFilter() = OptimizerFilter()

    @Bean
    open fun computeFullDistanceMatrixFilter() = ComputeFullDistanceMatrixFilter()

    @Bean
    open fun processUniDirectionNodesFilter() = ProcessUniDirectionNodesFilter()

    @Bean
    open fun removeLowerMatrixDiagonalFilter() = RemoveLowerMatrixDiagonalFilter()

    @Bean
    open fun outgoingParserFilter() = OutgoingParserFilter()

    @Bean
    open fun computeDistanceMatrixForOverwatchPipeline() = ComputeDistanceMatrixForOverwatchPipeline(
        incomingParserFilter = incomingParserFilter(),
        optimizerFilter = optimizerFilter(),
        computeFullDistanceMatrixFilter = computeFullDistanceMatrixFilter(),
        processUniDirectionNodesFilter = processUniDirectionNodesFilter(),
        removeLowerMatrixDiagonalFilter = removeLowerMatrixDiagonalFilter(),
        outgoingParserFilter = outgoingParserFilter()
    )

    @Bean
    open fun restController() = RestController(computeDistanceMatrixForOverwatchPipeline())

    @Bean
    open fun presentationController() = PresentationController()
}