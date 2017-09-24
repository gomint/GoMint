/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class TileEntity {

    // CHECKSTYLE:OFF
    @Getter
    protected Location location;
    // CHECKSTYLE:ON

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    TileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        this.location = new Location(
                world,
                tagCompound.getInteger( "x", 0 ),
                tagCompound.getInteger( "y", -1 ),
                tagCompound.getInteger( "z", 0 )
        );
    }

    /**
     * Tick this tileEntity
     *
     * @param currentMillis The amount of millis to save some CPU
     * @param dF            The percentage of the second which was calculated in the last tick
     */
    public abstract void update( long currentMillis, float dF );

    public void interact( Entity entity, int face, Vector facePos, ItemStack item ) {

    }

    /**
     * Save this TileEntity back to an compound
     *
     * @param compound The Compound which should be used to save the data into
     */
    public void toCompound( NBTTagCompound compound ) {
        compound.addValue( "x", (int) this.location.getX() );
        compound.addValue( "y", (int) this.location.getY() );
        compound.addValue( "z", (int) this.location.getZ() );
    }

}
