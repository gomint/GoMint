/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InventoryComponent extends AbstractTileEntityComponent {

    private final ContainerInventory<?> inventory;

    public InventoryComponent(TileEntity entity, Items items, ContainerInventory<?> inventory) {
        super(entity, items);
        this.inventory = inventory;
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer) {
            ( (EntityPlayer) entity ).openInventory( this.inventory );
        }
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        if (reason == SerializationReason.PERSIST) {
            this.writeInventory(compound, this.inventory);
        }
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        this.readInventory(compound, this.inventory);
    }

    @Override
    public void update(long currentTimeMS, float dT) {

    }

}
