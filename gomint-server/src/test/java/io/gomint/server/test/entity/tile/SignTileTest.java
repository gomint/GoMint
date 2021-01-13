/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test.entity.tile;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
            public BlockType blockType() {
                return null;
            }

            @Override
            public Location location() {
                return new Location( null, 0, 0, 0 );
            }
        };
    }

    @Test
    public void throwOnTooMuchLines() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SignTileEntity tileEntity = new SignTileEntity(this.getBlock(), null, null);

            NBTTagCompound compound = new NBTTagCompound("");
            compound.addValue("Text", "\n\n\n\n");
            tileEntity.applyClientData(null, compound);
        });
    }

    @Test
    public void accept3Lines() throws Exception {
        SignTileEntity tileEntity = new SignTileEntity( this.getBlock(), null, null );

        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Text", "\n\n\n" );
        tileEntity.applyClientData( null, compound );
    }

    @Test
    public void throwOnTooLongLine() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SignTileEntity tileEntity = new SignTileEntity(this.getBlock(), null, null);

            NBTTagCompound compound = new NBTTagCompound("");
            compound.addValue("Text", "hjkhjkgkhjgjgjhjhjk");
            tileEntity.applyClientData(null, compound);
        });
    }

}
