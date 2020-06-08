/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnderChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnderChestTileEntity extends ContainerTileEntity implements InventoryHolder {

    private EnderChestInventory inventory;

    /**
     * Create new ender chest based on the position
     *
     * @param block where the ender chest should be placed
     */
    public EnderChestTileEntity( Block block ) {
        super( block );
        this.inventory = new EnderChestInventory( this );
    }

    @Override
    public void update( long currentMillis ) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            ( (EntityPlayer) entity ).openInventory( this.inventory );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );
        compound.addValue( "id", "EnderChest" );
    }

    /**
     * Get the inventory of this ender chest
     *
     * @return
     */
    public EnderChestInventory getInventory() {
        return this.inventory;
    }

}
