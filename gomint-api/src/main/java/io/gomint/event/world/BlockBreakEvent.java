/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.world;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockBreakEvent extends CancellablePlayerEvent {

    private final Block breakBlock;

    /**
     * Create new block break event. This event gets called when a player decides to break a block
     *
     * @param player which breaks the block
     * @param breakBlock which should be broken
     */
    public BlockBreakEvent( EntityPlayer player, Block breakBlock ) {
        super( player );
        this.breakBlock = breakBlock;
    }

    /**
     * Get block which should be broken
     *
     * @return block which should be broken
     */
    public Block getBreakBlock() {
        return this.breakBlock;
    }

}
