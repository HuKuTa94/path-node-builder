package com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hukuta94.pathnodebuilder.overwatch.application.port.`in`.ComputeDistanceMatrixUseCase
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Label
import javax.swing.*
import javax.swing.text.JTextComponent

class DistanceMatrixCalculatorView(
    private val computeDistanceMatrixUseCase: ComputeDistanceMatrixUseCase
) {
    @Composable
    @Preview
    fun createView() = Box(
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
                        add(createComputeMatrixButton(inputTextPane, outputTextPane))
                        add(createTextPanel(outputTextPane))
                        add(Label("Copy computed distance matrix and paste it in OW Workshop"))
                    }
                }
            )
        }
    }

    private val inputTextPane = JTextPane()
    private val outputTextPane = JEditorPane().apply {
        this.isEditable = false
    }

    private fun createComputeMatrixButton(
        inputTextPane: JTextPane,
        outputTextPane: JEditorPane
    ) = JButton("Compute Distance Matrix").apply {
            this.alignmentX = Component.CENTER_ALIGNMENT
            this.addActionListener {
                outputTextPane.text = try {
                    computeDistanceMatrixUseCase.execute(inputTextPane.text)
                } catch (e: Exception) {
                    e.message
                }
            }
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
}