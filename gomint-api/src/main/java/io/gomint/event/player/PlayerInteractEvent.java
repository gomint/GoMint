package io.gomint.event.player;

import io.gomint.entity.Player;
import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerInteractEvent extends CancellablePlayerEvent {

    private ClickType clickType;
    private Block block;

    public PlayerInteractEvent( Player player, ClickType clickType, Block block ) {
        super( player );
        this.clickType = clickType;
        this.block = block;
    }

    /**
     * Get the click type
     *
     * @return enum value of the click type
     */
    public ClickType getClickType() {
        return clickType;
    }

    /**
     * Get the block with which the player interacted. This may be null
     *
     * @return block which the player interacted with
     */
    public Block getBlock() {
        return block;
    }

    public enum ClickType {
        /**
         * Right click
         */
        RIGHT,

        /**
         * Left click
         */
        LEFT
    }

}
