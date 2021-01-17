/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.BarrelTileEntity;
import io.gomint.server.entity.tileentity.LecternTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockBarrier;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
// TODO: Proper impl
@RegisterInfo( sId = "minecraft:barrel" )
public class Barrel extends Block {

    @Override
    public long breakTime() {
        return -1;
    }

    @Override
    public boolean onBreak( boolean creative ) {
        return creative;
    }

    @Override
    public float blastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BARREL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(BarrelTileEntity.class, compound, this, this.items);
    }

}
