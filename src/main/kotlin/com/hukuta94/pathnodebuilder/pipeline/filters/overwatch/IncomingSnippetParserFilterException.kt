package com.hukuta94.pathnodebuilder.pipeline.filters.overwatch

sealed class IncomingSnippetParserFilterException(
    override val message: String
) : RuntimeException() {

    class EmptyRawDataExceptionSnippet : IncomingSnippetParserFilterException("Raw data can not be empty")

    class NoRequiredVariablesExceptionSnippet(
        builderNodePositionsVarName: String,
        builderNodeConnectionsVarName: String
    ) : IncomingSnippetParserFilterException(
        """
        Raw data does not contain variables 
        'Global.$builderNodePositionsVarName' and/or 'Global.$builderNodeConnectionsVarName', or
        variables have incorrect format
        """.trimMargin()
    )
}