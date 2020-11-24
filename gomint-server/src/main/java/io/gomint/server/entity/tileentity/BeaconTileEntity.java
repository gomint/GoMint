/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Beacon")
public class BeaconTileEntity extends TileEntity {

    private int primary;
    private int secondary;

    /**
     * New beacon
     *
     * @param block which created this tile
     */
    public BeaconTileEntity( Block block, Items items ) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.primary = compound.getInteger( "primary", 0 );
        this.secondary = compound.getInteger( "secondary", 0 );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Beacon" );
        compound.addValue( "primary", this.primary );
        compound.addValue( "secondary", this.secondary );
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public void setSecondary(int secondary) {
        this.secondary = secondary;
    }

}
