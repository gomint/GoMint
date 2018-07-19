/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.postprocessor;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BedPostProcessor extends PostProcessor {

    public BedPostProcessor( WorldAdapter worldAdapter, BlockPosition position ) {
        super( worldAdapter, position );
    }

    @Override
    public void process() {
        // Check if we have a tile entity
        Block block = getBlock();
        if ( block.getRawTileEntity() == null ) {
            BlockPosition position = block.getLocation().toBlockPosition();

            NBTTagCompound compound = new NBTTagCompound( "" );

            // Add generic tile entity stuff
            compound.addValue( "x", position.getX() );
            compound.addValue( "y", position.getY() );
            compound.addValue( "z", position.getZ() );

            compound.addValue( "color", (byte) 14 );

            BedTileEntity tileEntity = new BedTileEntity( compound, this.worldAdapter );
            this.worldAdapter.storeTileEntity( position, tileEntity );
        }
    }

}
