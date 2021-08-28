package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import lombok.AllArgsConstructor;

/**
 * Represents code snippet of actions for the workshop's rule of the following structure:
 * variables
 * {
 * 	global:
 * 		17: BuilderNodePositions
 * }
 *
 * actions
 * {
 * 	Global.BuilderNodePositions =
 * 		Array(
 * 			Vector(-16.004, 0.350, -15.965),
 * 			Vector(-15.994, 0.350, -0.043),
 * 			Vector(-15.981, 0.350, 15.908),
 * 		);
 * }
 */
@AllArgsConstructor
public class RuleActionCodeSnippet
{
    private final Variables variables;
    private final Actions actions;

    @Override
    public String toString()
    {
        return
            variables.toString() +
            "\n\n" +
            actions.toString();
    }
}
