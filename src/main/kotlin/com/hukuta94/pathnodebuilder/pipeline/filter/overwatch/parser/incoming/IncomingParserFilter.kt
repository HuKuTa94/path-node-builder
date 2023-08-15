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

                splitString[0].withoutGlobalPrefix() to splitString[1].withoutOuterArray()
            }

        if (globalVariables.size < VARIABLES_COUNT) {
            throw IncomingParserFilterException.NoRequiredVariablesException(
                BUILDER_NODE_POSITIONS_VAR_NAME,
                BUILDER_NODE_CONNECTIONS_VAR_NAME
            )
        }

        return ParsedIncomingData(
            builderNodePositions = parseBuilderNodePositions(globalVariables),
            builderNodeConnections = emptyList()
        )
    }

    private fun parseBuilderNodePositions(globalVariables: Map<String, String>): List<Vector?> {
        return requireNotNull(globalVariables[BUILDER_NODE_POSITIONS_VAR_NAME])
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
     * Unwraps outer array.
     *
     * Example:
     * String "Array(Vector(x, y, z), Vector(x, y, z))"
     * will be unwrapped to "Vector(x, y, z), Vector(x, y, z)"
     */
    private fun String.withoutOuterArray() =
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