/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LogType;

import java.util.List;

/**
 * @author derklaro
 * @version 1.0
 * @stability 3
 */
public interface BlockSign<B> extends Block {

    /**
     * Get a copy of all lines on this sign. The maximum size of
     * this list is 4.
     *
     * @return list of all lines
     */
    List<String> lines();

    /**
     * Set a specific line to new content. When you set line 2 and there is no other line
     * line 1 will be empty string
     *
     * @param line    which should be set
     * @param content which should be set on that line
     * @return block for chaining
     */
    B line(int line, String content);

    /**
     * Get a specific line of the sign content
     *
     * @param line which you want to get
     * @return string or null when not set
     */
    String line(int line);

    /**
     * Get the type of wood from which this button has been made
     *
     * @return type of wood
     */
    LogType type();

    /**
     * Set the type of wood for this button
     *
     * @param logType type of wood
     * @return block for chaining
     */
    B type(LogType logType);

}
