/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class BeaconTileEntity extends TileEntity {

    private int primary;
    private int secondary;

    /**
     * New beacon
     *
     * @param primary   id of the first effect
     * @param secondary id of the second effect
     * @param location  of the beacon
     */
    public BeaconTileEntity( int primary, int secondary, Location location ) {
        super( location );

        this.primary = primary;
        this.secondary = secondary;
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param compound The TagCompound which should be used to read data from
     * @param world    The world in which this TileEntity resides
     */
    public BeaconTileEntity( NBTTagCompound compound, WorldAdapter world ) {
        super( compound, world );

        this.primary = compound.getInteger( "primary", 0 );
        this.secondary = compound.getInteger( "secondary", 0 );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Beacon" );
        compound.addValue( "primary", this.primary );
        compound.addValue( "secondary", this.secondary );
    }

}
