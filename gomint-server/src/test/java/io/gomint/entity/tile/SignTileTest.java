/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.tile;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;
import org.junit.Test;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignTileTest {

    private Block getBlock() {
        return new Block() {
            @Override
            public float getBlastResistance() {
                return 0;
            }

            @Override
            public BlockType getBlockType() {
                return null;
            }

            @Override
            public Location getLocation() {
                return new Location( null, 0, 0, 0 );
            }
        };
    }

    @Test( expected = IllegalArgumentException.class )
    public void throwOnTooMuchLines() throws Exception {
        SignTileEntity tileEntity = new SignTileEntity( this.getBlock() );

        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Text", "\n\n\n\n" );
        tileEntity.applyClientData( null, compound );
    }

    @Test
    public void accept3Lines() throws Exception {
        SignTileEntity tileEntity = new SignTileEntity( this.getBlock() );

        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Text", "\n\n\n" );
        tileEntity.applyClientData( null, compound );
    }

    @Test( expected = IllegalArgumentException.class )
    public void throwOnTooLongLine() throws Exception {
        SignTileEntity tileEntity = new SignTileEntity( this.getBlock() );

        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Text", "hjkhjkgkhjgjgjhjhjk" );
        tileEntity.applyClientData( null, compound );
    }

}
