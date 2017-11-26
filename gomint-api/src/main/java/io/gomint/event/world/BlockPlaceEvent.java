/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.world;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockPlaceEvent extends CancellablePlayerEvent {

    private final Block placedAgainst;
    private final Block shouldReplace;
    private final ItemStack item;

    /**
     * Created when a player wants to place a block
     *
     * @param player which wants to place the block
     * @param placedAgainst against which block did the player place
     * @param shouldReplace block which should be replaced with the item
     * @param itemStack which should replace above block
     */
    public BlockPlaceEvent( EntityPlayer player, Block placedAgainst, Block shouldReplace, ItemStack itemStack ) {
        super( player );
        this.placedAgainst = placedAgainst;
        this.shouldReplace = shouldReplace;
        this.item = itemStack;
    }

    /**
     * Get the block which the player placed against
     *
     * @return block which got placed against
     */
    public Block getPlacedAgainst() {
        return this.placedAgainst;
    }

    /**
     * Get the block which should be replaced
     *
     * @return block which should be replaced
     */
    public Block getShouldReplace() {
        return this.shouldReplace;
    }

    /**
     * Get the item which is going to be used to replace the block
     *
     * @return item which should replace the block
     */
    public ItemStack getItem() {
        return this.item;
    }

}
