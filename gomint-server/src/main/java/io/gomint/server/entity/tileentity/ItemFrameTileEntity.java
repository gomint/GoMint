/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.ItemComponent;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "ItemFrame")
public class ItemFrameTileEntity extends TileEntity {

    private final ItemComponent itemComponent;
    private float itemDropChance = 1f;
    private float itemRotation;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public ItemFrameTileEntity(Block block, Items items) {
        super(block, items);
        this.itemComponent = new ItemComponent(this, items, "Item");
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        //
        this.itemDropChance = compound.getFloat("ItemDropChance", 1.0f);
        this.itemRotation = compound.getFloat("ItemRotation", 0f);

        //
        this.itemComponent.fromCompound(compound);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, io.gomint.inventory.item.ItemStack item) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "ItemFrame");

        if (reason == SerializationReason.PERSIST) {
            compound.addValue("ItemDropChance", this.itemDropChance);
        }

        compound.addValue("ItemRotation", this.itemRotation);

        this.itemComponent.toCompound(compound, reason);
    }

}
