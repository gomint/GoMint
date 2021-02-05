/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class TileEntity {

    // CHECKSTYLE:OFF
    protected final Block block;
    protected final Items items;
    private byte moveable;
    protected boolean needsPersistence;
    // CHECKSTYLE:ON

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    TileEntity(Block block, Items items) {
        this.items = items;
        this.block = block;
        this.moveable = 1;
    }

    /**
     * Tick this tileEntity exactly once per 50 ms
     *
     * @param currentMillis The amount of millis to save some CPU
     */
    public abstract void update(long currentMillis, float dT);

    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {

    }

    /**
     * Load this tile entity from a compound
     *
     * @param compound which holds data for this tile entity
     */
    public void fromCompound(NBTTagCompound compound) {
        this.moveable = compound.getByte("isMovable", (byte) 1);
    }

    /**
     * Save this TileEntity back to an compound
     *
     * @param compound The Compound which should be used to save the data into
     * @param reason   why should this tile be serialized?
     */
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        BlockPosition position = this.block.position();

        compound.addValue("x", position.x());
        compound.addValue("y", position.y());
        compound.addValue("z", position.z());

        if (reason == SerializationReason.PERSIST) {
            compound.addValue("isMovable", this.moveable);
        }
    }

    public boolean isNeedsPersistence() {
        return this.needsPersistence;
    }

    public void resetPersistenceFlag() {
        this.needsPersistence = false;
    }

    public void applyClientData(EntityPlayer player, NBTTagCompound compound) throws Exception {

    }

    public Block getBlock() {
        return this.block;
    }

}
