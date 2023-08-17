package com.hukuta94.pathnodebuilder.pipeline.filters.overwatch

import com.hukuta94.pathnodebuilder.pipeline.common.Vector
import com.hukuta94.pathnodebuilder.pipeline.dto.ParsedIncomingDto
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OverwatchGlobalVariables.INPUT_NODE_CONNECTIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.pipeline.filters.overwatch.OverwatchGlobalVariables.INPUT_NODE_POSITIONS_VAR_NAME
import java.util.function.Function

class IncomingSnippetParserFilter : Function<String, ParsedIncomingDto> {

    override fun apply(rawData: String): ParsedIncomingDto {
        if (rawData.isBlank()) {
            throw IncomingSnippetParserFilterException.EmptyRawDataExceptionSnippet()
        }

        val matchResultIterator = PATTERN_REQUIRED_INPUT_VARIABLES.findAll(rawData).iterator()
        val globalVariables = matchResultIterator.asSequence()
            .associate {
                // Raw string looks like: "    Global.BuilderNodePositions = Array(Vector(-16.004, 0.350, -15.965))"
                val splitString = it.value.split('=')

                splitString[0].withoutGlobalPrefix() to splitString[1].unwrapArray()
            }

        if (globalVariables.size < COUNT_OF_REQUIRED_INPUT_VARIABLES) {
            throw IncomingSnippetParserFilterException.NoRequiredVariablesExceptionSnippet(
                INPUT_NODE_POSITIONS_VAR_NAME,
                INPUT_NODE_CONNECTIONS_VAR_NAME
            )
        }

        return ParsedIncomingDto(
            builderNodePositions = parseBuilderNodePositions(globalVariables),
            builderNodeConnections = parseBuilderNodeConnections(globalVariables)
        )
    }

    private fun parseBuilderNodePositions(globalVariables: Map<String, String>): List<Vector?> {
        return parseGlobalVariable(globalVariables.builderNodePositions()) { vector ->
            val coordinates = vector.splitCoordinates()
            Vector(
                x = coordinates[0].toDouble(),
                y = coordinates[1].toDouble(),
                z = coordinates[2].toDouble()
            )
        }
    }

    private fun parseBuilderNodeConnections(globalVariables: Map<String, String>): List<IntArray?> {
        return parseGlobalVariable(globalVariables.builderNodeConnections()) { array ->
            array.unwrapArray()
                .split(PATTERN_ARRAY_ELEMENT_SPLITTER)
                .asSequence()
                .map {
                    it.trim()
                      .toInt()
                }
                .toList()
                .toIntArray()
        }
    }

    private fun <T> parseGlobalVariable(
        globalVariableValue: String,
        parseFunction: (String) -> T
    ): List<T?> {
        return globalVariableValue
            .split(PATTERN_VARIABLE_VALUE_SPLITTER)
            .asSequence()
            .map {
                var value: T? = null

                if (!it.startsWith('F'))
                    value = parseFunction.invoke(it)

                value
            }.toList()
    }

    private fun Map<String, String>.builderNodePositions() =
        requireNotNull(this[INPUT_NODE_POSITIONS_VAR_NAME])

    private fun Map<String, String>.builderNodeConnections() =
        requireNotNull(this[INPUT_NODE_CONNECTIONS_VAR_NAME])

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
    private fun String.splitCoordinates(): List<String> {
        val matchResult = requireNotNull(PATTERN_SPLIT_VECTOR_TO_COORDS.find(this))

        return matchResult.groupValues.let {
            // skip the first matched result because it contains whole string (all coordinates)
            it.subList(1, it.size)
        }
    }

    companion object {
        private const val COUNT_OF_REQUIRED_INPUT_VARIABLES = 2

        private val PATTERN_REQUIRED_INPUT_VARIABLES = Regex(
            pattern = " ?\\s*Global\\.BuilderNode(Positions|Connections)\\s*=\\s*Array\\((?<array>[aA-zZ,(\\-\\d.\\s)]+)",
            option = RegexOption.MULTILINE
        )

        private val PATTERN_VARIABLE_VALUE_SPLITTER = Regex(
            ",\\s*(?=[FAV])"
        )

        private val PATTERN_SPLIT_VECTOR_TO_COORDS = Regex(
            "(-?\\d+\\.?\\d+)\\s*,\\s*(-?\\d+\\.?\\d+)\\s*,\\s*(-?\\d+\\.?\\d+)"
        )

        private val PATTERN_ARRAY_ELEMENT_SPLITTER = Regex(
            ",\\s*"
        )
    }
}