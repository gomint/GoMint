/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.SmeltingComponent;
import io.gomint.server.inventory.FurnaceInventory;
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
@RegisterInfo(sId = "Furnace")
public class FurnaceTileEntity extends ContainerTileEntity implements InventoryHolder {

    private final SmeltingComponent smeltingComponent;
    private final FurnaceInventory inventory;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param block of the tile entity
     */
    public FurnaceTileEntity(Block block, Items items) {
        super(block, items);

        this.inventory = new FurnaceInventory(items, this);
        this.smeltingComponent = new SmeltingComponent(this.inventory, this, this.items);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);
        this.smeltingComponent.fromCompound(compound);
    }


    @Override
    public void update(long currentMillis, float dT) {
        this.smeltingComponent.update(currentMillis, dT);
    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, io.gomint.inventory.item.ItemStack item) {
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).openInventory(this.inventory);
            this.smeltingComponent.interact(entity, face, facePos, item);
        }
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Furnace");
        this.smeltingComponent.toCompound(compound, reason);
    }

    public FurnaceInventory getInventory() {
        return inventory;
    }

}
