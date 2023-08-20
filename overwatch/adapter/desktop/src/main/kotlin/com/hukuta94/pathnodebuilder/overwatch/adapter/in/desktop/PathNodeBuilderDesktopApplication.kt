package com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hukuta94.pathnodebuilder.calculator.filter.ComputeUnitDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.application.ComputeDistanceMatrixPipeline
import com.hukuta94.pathnodebuilder.overwatch.application.port.`in`.ComputeDistanceMatrixUseCase
import com.hukuta94.pathnodebuilder.overwatch.filter.*
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Label
import javax.swing.*
import javax.swing.text.JTextComponent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Path Node Builder Desktop Application",
        resizable = true
    ) {
        app(injectComputeUnitDistanceMatrixUseCase())
    }
}

@Composable
@Preview
fun app(computeDistanceMatrixUseCase: ComputeDistanceMatrixUseCase) {
    val inputTextPane = JTextPane()
    val outputTextPane = JEditorPane().apply {
        this.isEditable = true
    }

    val computeMatrixAction: () -> Unit = {
        outputTextPane.text = try {
            computeDistanceMatrixUseCase.execute(inputTextPane.text)
        } catch (e: Exception) {
            e.message
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        ) {
            SwingPanel(
                background = Color.White,
                modifier = Modifier.size(1920.dp, 1280.dp),
                factory = {
                    JPanel().apply {
                        layout = BoxLayout(this, BoxLayout.Y_AXIS)
                        add(Label("Paste global variables from OW Workshop"))
                        add(createTextPanel(inputTextPane))
                        add(actionButton("Compute Distance Matrix", computeMatrixAction))
                        add(createTextPanel(outputTextPane))
                        add(Label("Copy computed distance matrix and paste it in OW Workshop"))
                    }
                }
            )
        }
    }
}

fun actionButton(
    text: String,
    action: () -> Unit
): JButton {
    val button = JButton(text)
    button.alignmentX = Component.CENTER_ALIGNMENT
    button.addActionListener { action() }

    return button
}

private fun createTextPanel(textComponent: JTextComponent) =
    JPanel(BorderLayout()).apply {
        this.add(
            JScrollPane(textComponent).apply {
                this.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                this.preferredSize = Dimension(700, 700)
            }
        )
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

