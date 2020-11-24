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

@RegisterInfo(sId = "BrewingStand")
public class BrewingStandTileEntity extends TileEntity {

    private short cookTime;
    private short fuelTotal;
    private short fuelAmount;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public BrewingStandTileEntity(Block block, Items items) {
        super(block, items);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        this.cookTime = compound.getShort("CookTime", (short) 0);
        this.fuelTotal = compound.getShort("FuelTotal", (short) 0);
        this.fuelAmount = compound.getShort("FuelAmount", (short) 0);


    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);
        compound.addValue("id", "BrewingStand");
    }

}
