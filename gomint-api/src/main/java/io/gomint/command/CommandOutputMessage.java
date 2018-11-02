/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class CommandOutputMessage {

    private boolean success;
    private String format;
    private List<String> parameters;

}
