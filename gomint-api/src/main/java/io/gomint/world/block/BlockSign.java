/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockSign extends Block {

    /**
     * Get a copy of all lines on this sign. The maximum size of
     * this list is 4.
     *
     * @return list of all lines
     */
    List<String> getLines();

    /**
     * Set a specific line to new content. When you set line 2 and there is no other line
     * line 1 will be empty string
     *
     * @param line    which should be set
     * @param content which should be set on that line
     */
    void setLine( int line, String content );

    /**
     * Get a specific line of the sign content
     *
     * @param line which you want to get
     * @return string or null when not set
     */
    String getLine( int line );

}
