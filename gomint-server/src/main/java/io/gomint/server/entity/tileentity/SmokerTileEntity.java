/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.SmokerInventory;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

public class SmokerTileEntity extends TileEntity implements InventoryHolder {

    private final SmokerInventory inventory;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public SmokerTileEntity(Block block) {
        super(block);

        this.inventory = new SmokerInventory( this );
    }

    @Override
    public void update(long currentMillis) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Smoker");
    }

}
