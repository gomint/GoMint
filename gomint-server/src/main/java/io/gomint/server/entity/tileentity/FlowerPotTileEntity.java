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
import io.gomint.server.entity.component.BlockIdentifierComponent;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "FlowerPot")
public class FlowerPotTileEntity extends TileEntity implements InventoryHolder {

    private BlockIdentifierComponent blockIdentifierComponent;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public FlowerPotTileEntity(Block block, Items items) {
        super(block, items);
        this.blockIdentifierComponent = new BlockIdentifierComponent(this, items, "PlantBlock");
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        this.blockIdentifierComponent.fromCompound(compound);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "FlowerPot");
        this.blockIdentifierComponent.toCompound(compound, reason);
    }

    public BlockIdentifier getHolding() {
        return this.blockIdentifierComponent.getBlockIdentifier();
    }

}
