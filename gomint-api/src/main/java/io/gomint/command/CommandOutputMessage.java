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

    private boolean success;
    private String format;
    private List<String> parameters;

    public CommandOutputMessage(boolean success, String format, List<String> parameters) {
        this.success = success;
        this.format = format;
        this.parameters = parameters;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFormat() {
        return format;
    }

    public List<String> getParameters() {
        return parameters;
    }

}
