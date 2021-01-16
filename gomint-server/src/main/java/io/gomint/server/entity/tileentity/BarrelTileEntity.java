/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.InventoryComponent;
import io.gomint.server.inventory.BarrelInventory;
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
@RegisterInfo(sId = "Barrel")
public class BarrelTileEntity extends ContainerTileEntity implements InventoryHolder {

    private final BarrelInventory inventory;
    private final InventoryComponent inventoryComponent;

    public BarrelTileEntity(Block block, Items items) {
        super(block, items);
        this.inventory = new BarrelInventory(items, this);
        this.inventoryComponent = new InventoryComponent(this, items, this.inventory);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);
        this.inventoryComponent.fromCompound(compound);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        this.inventoryComponent.interact(entity, face, facePos, item);
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);
        compound.addValue("id", "Barrel");
        this.inventoryComponent.toCompound(compound, reason);
    }

    /**
     * Get this chests inventory
     *
     * @return inventory of this tile
     */
    public BarrelInventory getInventory() {
        return this.inventory;
    }

}
