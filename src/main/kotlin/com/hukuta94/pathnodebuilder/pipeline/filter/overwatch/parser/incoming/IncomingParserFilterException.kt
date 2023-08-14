package com.hukuta94.pathnodebuilder.pipeline.filter.overwatch.parser.incoming

sealed class IncomingParserFilterException(
    override val message: String
) : RuntimeException() {

    class EmptyRawDataException : IncomingParserFilterException("Raw data can not be empty")

    class NoRequiredVariablesException(
        builderNodePositionsVarName: String,
        builderNodeConnectionsVarName: String
    ) : IncomingParserFilterException(
        """
        Raw data does not contain variables 
        'Global.$builderNodePositionsVarName' and/or 'Global.$builderNodeConnectionsVarName', or
        variables have incorrect format
        """.trimMargin()
    )
}