/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.command;

import io.gomint.command.CommandOverload;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandCanidate {

    private CommandOverload overload;
    private Map<String, Object> arguments;
    private boolean completedOptionals;
    private boolean readCompleted;

    public CommandCanidate(CommandOverload overload, Map<String, Object> arguments, boolean completedOptionals, boolean readCompleted) {
        this.overload = overload;
        this.arguments = arguments;
        this.completedOptionals = completedOptionals;
        this.readCompleted = readCompleted;
    }

    public CommandOverload getOverload() {
        return this.overload;
    }

    public Map<String, Object> getArguments() {
        return this.arguments;
    }

    public boolean isCompletedOptionals() {
        return this.completedOptionals;
    }

    public boolean isReadCompleted() {
        return this.readCompleted;
    }

    @Override
    public String toString() {
        return "CommandCanidate{" +
            "overload=" + this.overload +
            ", arguments=" + this.arguments +
            ", completedOptionals=" + this.completedOptionals +
            ", readCompleted=" + this.readCompleted +
            '}';
    }

}
