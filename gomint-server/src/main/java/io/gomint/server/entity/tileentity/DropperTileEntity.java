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
import io.gomint.server.entity.component.InventoryComponent;
import io.gomint.server.inventory.DropperInventory;
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
@RegisterInfo(sId = "Dropper")
public class DropperTileEntity extends ContainerTileEntity implements InventoryHolder {

    private final DropperInventory inventory;
    private final InventoryComponent inventoryComponent;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public DropperTileEntity(Block block, Items items) {
        super(block, items);

        this.inventory = new DropperInventory(items, this);
        this.inventoryComponent = new InventoryComponent(this, items, this.inventory);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        // Read in items
        this.inventoryComponent.fromCompound(compound);
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        this.inventoryComponent.interact(entity, face, facePos, item);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Dropper");
        this.inventoryComponent.toCompound(compound, reason);
    }

    public DropperInventory getInventory() {
        return inventory;
    }

}
