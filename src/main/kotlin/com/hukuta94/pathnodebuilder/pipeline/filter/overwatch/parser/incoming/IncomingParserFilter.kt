package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming

import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.BUILDER_NODE_CONNECTIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.BUILDER_NODE_POSITIONS_VAR_NAME
import com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.OverwatchGlobalVariables.VARIABLES_COUNT
import java.util.function.Function

internal class IncomingParserFilter : Function<String, ParsedIncomingData> {

    override fun apply(rawData: String): ParsedIncomingData {
        if (rawData.isBlank()) {
            throw IncomingParserFilterException.EmptyRawDataException()
        }

        val matchResultIterator = PATTERN_REQUIRED_VARIABLES.findAll(rawData).iterator()
        val parsedIncomingData = matchResultIterator.asSequence()
            .associate {
                // Raw string looks like: "    Global.BuilderNodePositions = Array(Vector(-16.004, 0.350, -15.965))"
                val splitString = it.value.split('=')

                // Remove "Global." from variable name
                splitString[0].substringAfter('.').trim() to splitString[1].trim()
            }

        if (parsedIncomingData.size < VARIABLES_COUNT) {
            throw IncomingParserFilterException.NoRequiredVariablesException(
                BUILDER_NODE_POSITIONS_VAR_NAME,
                BUILDER_NODE_CONNECTIONS_VAR_NAME
            )
        }

        return ParsedIncomingData(parsedIncomingData)
    }

    companion object {
        private val PATTERN_REQUIRED_VARIABLES = Regex(
            pattern = " ?\\s*Global\\.BuilderNode(Positions|Connections)\\s*=\\s*Array\\((?<array>[aA-zZ,(\\-\\d.\\s)]+)",
            option = RegexOption.MULTILINE
        )
    }
}