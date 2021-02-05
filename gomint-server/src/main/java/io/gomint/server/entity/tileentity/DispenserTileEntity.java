/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.entity.component.InventoryComponent;
import io.gomint.server.inventory.DispenserInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Dispenser")
public class DispenserTileEntity extends TileEntity implements InventoryHolder {

    private final DispenserInventory inventory;
    private final InventoryComponent inventoryComponent;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     * @param items factory
     */
    public DispenserTileEntity(Block block, Items items) {
        super(block, items);
        this.inventory = new DispenserInventory(items, this);
        this.inventoryComponent = new InventoryComponent(this, items, this.inventory);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        // Read in items
        this.inventoryComponent.fromCompound(compound);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Dispenser");
        this.inventoryComponent.toCompound(compound, reason);
    }

    public DispenserInventory getInventory() {
        return this.inventory;
    }

}
