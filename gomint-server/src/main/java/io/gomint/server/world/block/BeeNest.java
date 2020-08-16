/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.BannerTileEntity;
import io.gomint.server.entity.tileentity.BeehiveTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:bee_nest")
public class BeeNest extends Block {

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity( compound );
        return this.world.getServer().getTileEntities().construct(BeehiveTileEntity.class, compound, this, this.world.getServer().getItems());
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEE_NEST;
    }

}
