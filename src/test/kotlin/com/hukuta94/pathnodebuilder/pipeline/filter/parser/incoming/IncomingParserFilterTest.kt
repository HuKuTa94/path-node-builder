package com.hukuta94.pathnodebuilder.pipeline.filter.parser.incoming

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming.IncomingParserFilter
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming.IncomingParserFilterException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class IncomingParserFilterTest {

    private val filter = IncomingParserFilter()

    @Test
    fun `should be error when raw data is empty`() {
        assertThrows<IncomingParserFilterException.EmptyRawDataException> { filter.apply("") }
        assertThrows<IncomingParserFilterException.EmptyRawDataException> { filter.apply(" ") }
    }

    @Test
    fun `should be error when raw data does not contain required variables`() {
        val rawIncomingData = """
            variables
            {
                global:
                    0: NotRequiredVariableName
            }

            actions
            {
                Global.NotRequiredVariableName = False;
            }
            """.trimIndent()

        assertThrows<IncomingParserFilterException.NoRequiredVariablesException> { filter.apply(rawIncomingData) }
    }

    @Test
    fun `should be error when raw data contains required variables with incorrect format`() {
        val rawIncomingData = """
            variables
            {
                global:
                    17: BuilderNodePositions
		            18: BuilderNodeConnections
            }

            actions
            {
                Global.BuilderNodePositions = ;
                Global.BuilderNodeConnections = 1;
            }
            """.trimIndent()

        assertThrows<IncomingParserFilterException.NoRequiredVariablesException> { filter.apply(rawIncomingData) }
    }

    @Test
    fun `should be error when raw data contains BuilderNodeConnections variable with incorrect format`() {
        val rawIncomingData = """
            variables
            {
                global:
                    17: BuilderNodePositions
		            18: BuilderNodeConnections
            }

            actions
            {
                Global.BuilderNodePositions = Array();
                Global.BuilderNodeConnections = 1;
            }
            """.trimIndent()

        assertThrows<IncomingParserFilterException.NoRequiredVariablesException> { filter.apply(rawIncomingData) }
    }

    @Test
    fun `should be error when raw data contains BuilderNodePositions variable with incorrect format`() {
        val rawIncomingData = """
            variables
            {
                global:
                    17: BuilderNodePositions
		            18: BuilderNodeConnections
            }

            actions
            {
                Global.BuilderNodePositions = False;
                Global.BuilderNodeConnections = Array(Array(5, 1, 6);;
            }
            """.trimIndent()

        assertThrows<IncomingParserFilterException.NoRequiredVariablesException> { filter.apply(rawIncomingData) }
    }

    @Test
    fun `should parse succeed when raw data contains required variables with correct values`() {
        val rawIncomingData = """
            variables
            {
                global:
                    17: BuilderNodePositions
		            18: BuilderNodeConnections
            }

            actions
            {
                Global.BuilderNodePositions = Array(Vector(-16.004, 0.350, -15.965), False, Vector(2, 1, 20.051));
                Global.BuilderNodeConnections = Array(Array(5, 1, 6), False, Array(1, 5, 3));
            }
            """.trimIndent()

        val actualResult = filter.apply(rawIncomingData)

        val expectedNodePositions = listOf(
            Vector(-16.004, 0.350, -15.965),
            null,
            Vector(2.000, 1.000, 20.051)
        )

        val expectedNodeConnection: List<IntArray> = listOf(
            intArrayOf(5, 1, 6),
            intArrayOf(),
            intArrayOf(1, 5, 3)
        )

        assertEquals(expectedNodePositions, actualResult.builderNodePositions)

        //TODO Имплементировать парсинг массива связей между нодами
        assertEquals(expectedNodeConnection, actualResult.builderNodeConnections)
    }
}