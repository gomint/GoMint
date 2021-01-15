/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class CommandOutputMessage {

    private final boolean success;
    private final String format;
    private final List<String> parameters;

    public CommandOutputMessage(boolean success, String format, List<String> parameters) {
        this.success = success;
        this.format = format;
        this.parameters = parameters;
    }

    public boolean success() {
        return success;
    }

    public String format() {
        return format;
    }

    public List<String> parameters() {
        return parameters;
    }

}
