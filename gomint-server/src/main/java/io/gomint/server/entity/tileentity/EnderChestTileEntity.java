/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "EnderChest")
public class EnderChestTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Create new ender chest based on the position
     *
     * @param block where the ender chest should be placed
     */
    public EnderChestTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) entity;
            player.getEnderChestInventory().setContainerPosition( this.block.getPosition() );
            player.openInventory( player.getEnderChestInventory() );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );
        compound.addValue( "id", "EnderChest" );
    }

}
