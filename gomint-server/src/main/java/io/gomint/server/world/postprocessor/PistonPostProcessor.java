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
import io.gomint.server.util.Things;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Air;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.StickyPiston;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PistonPostProcessor extends PostProcessor {

    /**
     * Create a new piston fix processor
     *
     * @param worldAdapter in which world the piston is
     * @param position     of the corrupted piston
     */
    public PistonPostProcessor( WorldAdapter worldAdapter, BlockPosition position ) {
        super( worldAdapter, position );
    }

    @Override
    public void process() {
        // Check if we have a tile entity
        Block block = getBlock();
        if ( block.getRawTileEntity() == null ) {
            BlockPosition position = block.getLocation().toBlockPosition();

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

            // Store the new tile
            TileEntity tileEntity = new PistonArmTileEntity( compound, this.worldAdapter );
            this.worldAdapter.storeTileEntity( position, tileEntity );
        } else {
            // Is expanded
            if ( ( block.getBlockData() & 8 ) > 0 ) {
                PistonArmTileEntity tileEntity = block.getTileEntity();
                if ( !tileEntity.isExtended() ) {
                    tileEntity.setExtended( true );
                }

                block.setBlockData( (byte) ( block.getBlockData() - 8 ) );
            }
        }

        // We need to remove old piston head
        byte facing = block.getBlockData();
        if ( facing > 5 ) { // 6 sided piston lol
            block.setBlockData( (byte) 0 );
            facing = 0;
        }

        BlockFace face = Things.convertFromDataToBlockFace( facing );
        block.getSide( face ).setType( Air.class );
    }

}
