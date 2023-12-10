package com.hukuta94.pathnodebuilder.application

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hukuta94.pathnodebuilder.calculator.filter.ComputeUnitDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.desktop.DistanceMatrixCalculatorView
import com.hukuta94.pathnodebuilder.overwatch.application.port.ComputeDistanceMatrixPipeline
import com.hukuta94.pathnodebuilder.overwatch.application.port.`in`.ComputeDistanceMatrixUseCase
import com.hukuta94.pathnodebuilder.overwatch.filter.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Path Node Builder Desktop Application",
        resizable = true
    ) {
        DistanceMatrixCalculatorView(
            injectComputeUnitDistanceMatrixUseCase()
        ).createView()
    }
}

private fun injectComputeUnitDistanceMatrixUseCase(): ComputeDistanceMatrixUseCase {
    return ComputeDistanceMatrixPipeline(
        incomingSnippetParserFilter = IncomingSnippetParserFilter(),
        normalizerFilter = NormalizerFilter(),
        computeUnitDistanceMatrixFilter = ComputeUnitDistanceMatrixFilter(),
        processUniDirectionNodesFilter = ProcessUniDirectionNodesFilter(),
        removeBottomSymmetricalPartOfMatrixFilter = RemoveBottomSymmetricalPartOfMatrixFilter(),
        outgoingSnippetParserFilter = OutgoingSnippetParserFilter()
    )
}

