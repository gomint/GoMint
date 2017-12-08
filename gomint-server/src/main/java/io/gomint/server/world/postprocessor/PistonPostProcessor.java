/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.postprocessor;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.PistonArmTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.StickyPiston;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PistonPostProcessor extends PostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger( PistonPostProcessor.class );

    public PistonPostProcessor( WorldAdapter worldAdapter, BlockPosition position ) {
        super( worldAdapter, position );
    }

    @Override
    public void process() {
        // Check if we have a tile entity
        Block block = getBlock();
        if ( block.getTileEntity() == null ) {
            BlockPosition position = block.getLocation().toBlockPosition();
            LOGGER.warn( "We need to fix piston head @ {}", position );

            NBTTagCompound compound = new NBTTagCompound( "" );

            // Look around for piston we attach to
            compound.addValue( "Sticky", block instanceof StickyPiston ? (byte) 1 : (byte) 0 );

            // Add generic tile entity stuff
            compound.addValue( "x", position.getX() );
            compound.addValue( "y", position.getY() );
            compound.addValue( "z", position.getZ() );

            // Is expanded
            if ( ( block.getBlockData() & 8 ) > 0 ) {
                compound.addValue( "State", (byte) 2 );
                compound.addValue( "NewState", (byte) 2 );

                compound.addValue( "Progress", 1f );
                compound.addValue( "LastProgress", 1f );

                block.setBlockData( (byte) ( block.getBlockData() - 8 ) );
            }

            TileEntity tileEntity = new PistonArmTileEntity( compound, this.worldAdapter );
            this.worldAdapter.storeTileEntity( position, tileEntity );
        }
    }

}
