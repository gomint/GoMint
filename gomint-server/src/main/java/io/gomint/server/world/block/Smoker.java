/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.SmokerTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:smoker")
public class Smoker extends Block {
    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOKER;
    }


    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        return new SmokerTileEntity( this );
    }

}
