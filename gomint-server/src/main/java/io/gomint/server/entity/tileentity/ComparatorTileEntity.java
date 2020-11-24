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
@RegisterInfo(sId = "Comparator")
public class ComparatorTileEntity extends TileEntity {

    private int outputSignal;

    public ComparatorTileEntity( Block block, Items items ) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.outputSignal = compound.getInteger( "OutputSignal", 0 );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Comparator" );
        compound.addValue( "OutputSignal", this.outputSignal );
    }

}
