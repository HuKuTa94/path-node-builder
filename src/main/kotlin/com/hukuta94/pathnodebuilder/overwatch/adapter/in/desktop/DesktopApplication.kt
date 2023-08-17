package com.hukuta94.pathnodebuilder.overwatch.adapter.`in`.desktop

import com.hukuta94.pathnodebuilder.calculator.filter.ComputeUnitDistanceMatrixFilter
import com.hukuta94.pathnodebuilder.overwatch.application.ComputeDistanceMatrixPipeline
import com.hukuta94.pathnodebuilder.overwatch.filter.*
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*
import javax.swing.text.JTextComponent

fun main() {
    val computeDistanceMatrixPipeline = ComputeDistanceMatrixPipeline(
            incomingSnippetParserFilter = IncomingSnippetParserFilter(),
            normalizerFilter = NormalizerFilter(),
            computeUnitDistanceMatrixFilter = ComputeUnitDistanceMatrixFilter(),
            processUniDirectionNodesFilter = ProcessUniDirectionNodesFilter(),
            removeBottomSymmetricalPartOfMatrixFilter = RemoveBottomSymmetricalPartOfMatrixFilter(),
            outgoingSnippetParserFilter = OutgoingSnippetParserFilter()
        )

    val inputTextPane = JTextPane()
    val outputTextPane = JEditorPane().apply {
        this.isEditable = true
    }

    val inputPanel = createTextPanel(inputTextPane)
    val outputPanel = createTextPanel(outputTextPane)

    val computeMatrixBtn = JButton().apply {
        this.text = "Compute Matrix"
        this.addActionListener {
            outputTextPane.text = try {
                computeDistanceMatrixPipeline.execute(inputTextPane.text)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    JFrame().apply {
        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.title = "Path Node Builder Desktop"
        this.layout = FlowLayout()
        this.setSize(1600, 900)
        this.setLocationRelativeTo(null)
        this.background = Color.BLACK
        this.isVisible = true

        this.add(inputPanel)
        this.add(computeMatrixBtn)
        this.add(outputPanel)
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