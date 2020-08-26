/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public enum ParamType {

    /**
     * Select a entity
     */
    TARGET,

    /**
     * Set of strings to choose from
     */
    STRING_ENUM,

    /**
     * True or false
     */
    BOOL,

    /**
     * 4 byte long number
     */
    INT,

    /**
     * Block position containing x, y and z
     */
    BLOCK_POS,

    /**
     * String
     */
    STRING,

    /**
     * Text which consumes all remaining input
     */
    TEXT,

    /**
     * 4 byte decimal with normal precision
     */
    FLOAT,

    /**
     * Command
     */
    COMMAND,

}
