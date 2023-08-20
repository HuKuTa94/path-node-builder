package com.hukuta94.pathnodebuilder.application.configuration

import com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.web.PresentationController
import com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.web.RestController
import com.hukuta94.pathnodebuilder.overwatch.application.ComputeDistanceMatrixPipeline
import com.hukuta94.pathnodebuilder.calculator.filter.ComputeUnitDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.RemoveBottomSymmetricalPartOfMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.ProcessUniDirectionNodesFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.NormalizerFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.IncomingSnippetParserFilter
import com.hukuta94.pathnodebuilder.overwatch.filter.OutgoingSnippetParserFilter
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
    open fun computeUnitDistanceMatrixFilter() = ComputeUnitDistanceMatrixFilter()

    @Bean
    open fun processUniDirectionNodesFilter() = ProcessUniDirectionNodesFilter()

    @Bean
    open fun removeBottomSymmetricalPartOfMatrixFilter() = RemoveBottomSymmetricalPartOfMatrixFilter()

    @Bean
    open fun outgoingSnippetParserFilter() = OutgoingSnippetParserFilter()

    @Bean
    open fun computeDistanceMatrixPipeline() = ComputeDistanceMatrixPipeline(
        incomingSnippetParserFilter = incomingSnippetParserFilter(),
        normalizerFilter = normalizerFilter(),
        computeUnitDistanceMatrixFilter = computeUnitDistanceMatrixFilter(),
        processUniDirectionNodesFilter = processUniDirectionNodesFilter(),
        removeBottomSymmetricalPartOfMatrixFilter = removeBottomSymmetricalPartOfMatrixFilter(),
        outgoingSnippetParserFilter = outgoingSnippetParserFilter()
    )

    @Bean
    open fun restController() = RestController(computeDistanceMatrixPipeline())

    @Bean
    open fun presentationController() = PresentationController()
}