/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

@RegisterInfo(sId = "Beehive")
public class BeehiveTileEntity extends TileEntity {

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public BeehiveTileEntity(Block block, Items items) {
        super(block, items);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);
        compound.addValue("id", "Beehive");
    }

}
