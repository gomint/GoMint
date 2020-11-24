/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;
import io.gomint.inventory.item.ItemStack;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class PlayerDeathEvent extends PlayerEvent {

    private String deathMessage;
    private boolean dropInventory;
    private List<ItemStack> drops;

    public PlayerDeathEvent( EntityPlayer player, String deathMessage, boolean dropInventory, List<ItemStack> drops ) {
        super( player );
        this.deathMessage = deathMessage;
        this.dropInventory = dropInventory;
        this.drops = drops;
    }

    /**
     * Get the death message which will be displayed in chat
     *
     * @return death message, can be null
     */
    public String getDeathMessage() {
        return this.deathMessage;
    }

    /**
     * Set a new death message
     *
     * @param deathMessage which will be used, can be null or empty string to not display anything
     */
    public void setDeathMessage( String deathMessage ) {
        this.deathMessage = deathMessage;
    }

    /**
     * Should the inventory of the player be dropped?
     *
     * @return true when if will be dropped, false when not
     */
    public boolean isDropInventory() {
        return this.dropInventory;
    }

    /**
     * Set the drop inventory flag
     *
     * @param dropInventory false will not drop the inventory, true will drop the inventory
     */
    public void setDropInventory( boolean dropInventory ) {
        this.dropInventory = dropInventory;
    }

    /**
     * A list of items which will be dropped if {@link #isDropInventory()} returns true
     *
     * @return list of items
     */
    public List<ItemStack> getDrops() {
        return drops;
    }

}
