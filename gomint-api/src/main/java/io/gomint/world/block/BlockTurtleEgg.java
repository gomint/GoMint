/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.CrackStatus;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockTurtleEgg extends Block {

    /**
     * Set the amount of eggs, if over 4 its capped to 4
     *
     * @param amountOfEggs from 1 - 4 capped on either side
     * @return block for chaining
     */
    BlockTurtleEgg amountOfEggs(int amountOfEggs);

    /**
     * Get the amount of eggs
     *
     * @return 1 - 4 eggs
     */
    int amountOfEggs();

    /**
     * Set the cracked status
     *
     * @param status which should be set
     * @return block for chaining
     */
    BlockTurtleEgg crackStatus(CrackStatus status);

    /**
     * Get the cracked status
     *
     * @return crack status
     */
    CrackStatus crackStatus();

}
