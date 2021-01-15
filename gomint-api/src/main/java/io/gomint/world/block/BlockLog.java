/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockLog extends Block, BlockAxis<BlockLog> {

    /**
     * Is this log stripped
     *
     * @return true when stripped, false when not
     */
    boolean stripped();

    /**
     * Set stripped status of this log
     *
     * @param stripped true when the log should be stripped, false if not
     * @return block for chaining
     */
    BlockLog stripped(boolean stripped);

    /**
     * Set the type of log
     *
     * @param type of log
     * @return block for chaining
     */
    BlockLog type(LogType type);

    /**
     * Get the type of this log
     *
     * @return type of log
     */
    LogType type();

    /**
     * Add bark textures to all sides or not
     *
     * @param allSides true when bark on all sides, false if not
     * @return block for chaining
     */
    BlockLog barkOnAllSides(boolean allSides);

    /**
     * Is bark on all sides?
     *
     * @return true when bark is on all sides, false when not
     */
    boolean barkOnAllSides();

}
