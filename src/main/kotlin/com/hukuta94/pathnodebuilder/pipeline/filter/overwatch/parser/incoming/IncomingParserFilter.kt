package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.BUILDER_NODE_CONNECTIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.BUILDER_NODE_POSITIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.VARIABLES_COUNT
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.Vector
import java.util.function.Function

internal class IncomingParserFilter : Function<String, ParsedIncomingData> {

    override fun apply(rawData: String): ParsedIncomingData {
        if (rawData.isBlank()) {
            throw IncomingParserFilterException.EmptyRawDataException()
        }

        val matchResultIterator = PATTERN_REQUIRED_VARIABLES.findAll(rawData).iterator()
        val globalVariables = matchResultIterator.asSequence()
            .associate {
                // Raw string looks like: "    Global.BuilderNodePositions = Array(Vector(-16.004, 0.350, -15.965))"
                val splitString = it.value.split('=')

                splitString[0].withoutGlobalPrefix() to splitString[1].unwrapArray()
            }

        if (globalVariables.size < VARIABLES_COUNT) {
            throw IncomingParserFilterException.NoRequiredVariablesException(
                BUILDER_NODE_POSITIONS_VAR_NAME,
                BUILDER_NODE_CONNECTIONS_VAR_NAME
            )
        }

        return ParsedIncomingData(
            builderNodePositions = parseBuilderNodePositions(globalVariables),
            builderNodeConnections = parseBuilderNodeConnections(globalVariables)
        )
    }

    private fun parseBuilderNodePositions(globalVariables: Map<String, String>): List<Vector?> {
        return globalVariables.builderNodePositions()
            .split(PATTERN_VARIABLE_VALUE_SPLITTER)
            .asSequence()
            .map { vector ->
                var value: Vector? = null
                if (!vector.startsWith('F')) { // skip "False" values and put as null
                    val coordinates = vector.splitCoordinates()
                    value = Vector(
                        x = coordinates[0].toDouble(),
                        y = coordinates[1].toDouble(),
                        z = coordinates[2].toDouble()
                    )
                }
                value
            }
            .toList()
    }

    private fun parseBuilderNodeConnections(globalVariables: Map<String, String>): List<List<Int>?> {
        return globalVariables.builderNodeConnections()
            .split(PATTERN_VARIABLE_VALUE_SPLITTER)
            .asSequence()
            .map { array ->
                var value: List<Int>? = null
                if (!array.startsWith('F')) { // skip "False" values and put as null
                    value = array.unwrapArray()
                        .split(", ")
                        .asSequence()
                        .map { it.toInt() }
                        .toList()
                }
                value
            }.toList()
    }

    private fun Map<String, String>.builderNodePositions() =
        requireNotNull(this[BUILDER_NODE_POSITIONS_VAR_NAME])

    private fun Map<String, String>.builderNodeConnections() =
        requireNotNull(this[BUILDER_NODE_CONNECTIONS_VAR_NAME])

    /**
     * Removes "Global." prefix from string.
     *
     * Example:
     * String "    Global.BuilderNodePositions "
     * will be trimmed to "BuilderNodePositions"
     */
    private fun String.withoutGlobalPrefix() =
        this.substringAfter('.')
            .trim()

    /**
     * Unwraps array.
     *
     * Example:
     * String "Array(Vector(x, y, z), Vector(x, y, z))"
     * will be unwrapped to "Vector(x, y, z), Vector(x, y, z)"
     */
    private fun String.unwrapArray() =
        this.substringAfter("Array(")
            .substringBeforeLast(')')

    /**
     * Extracts coordinates from vector.
     *
     * Example:
     * String "Vector(1, 2, 3)"
     * will be unwrapped to Array {1, 2, 3}
     *
     * @return array of coordinates
     */
    private fun String.splitCoordinates() =
        this.substringAfter("Vector(")
            .substringBeforeLast(')')
            .split(", ")

    companion object {
        private val PATTERN_REQUIRED_VARIABLES = Regex(
            pattern = " ?\\s*Global\\.BuilderNode(Positions|Connections)\\s*=\\s*Array\\((?<array>[aA-zZ,(\\-\\d.\\s)]+)",
            option = RegexOption.MULTILINE
        )

        private val PATTERN_VARIABLE_VALUE_SPLITTER = Regex(
            ",\\s*(?=[FAV])"
        )
    }
}