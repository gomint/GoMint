/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockLog extends Block, BlockAxis {

    /**
     * Is this log stripped
     *
     * @return true when stripped, false when not
     */
    boolean isStripped();

    /**
     * Set stripped status of this log
     *
     * @param stripped true when the log should be stripped, false if not
     */
    void setStripped( boolean stripped );

    /**
     * Set the type of log
     *
     * @param type of log
     */
    void setLogType( LogType type );

    /**
     * Get the type of this log
     *
     * @return type of log
     */
    LogType getLogType();

}
