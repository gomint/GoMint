/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.SmokerTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSmoker;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

@RegisterInfo(sId = "minecraft:smoker", def = true)
@RegisterInfo(sId = "minecraft:lit_smoker")
public class Smoker extends Block implements BlockSmoker {

    private static final BlockfaceBlockState FACING = new BlockfaceBlockState(() -> new String[]{"facing_direction"});

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
        super.createTileEntity( compound );
        return this.tileEntities.construct(SmokerTileEntity.class, compound, this, this.items);
    }

    @Override
    public boolean isBurning() {
        return this.getBlockId().equals("minecraft:lit_smoker");
    }

    @Override
    public void setBurning(boolean burning) {
        if (burning) {
            this.setBlockId("minecraft:lit_smoker");
        } else {
            this.setBlockId("minecraft:smoker");
        }
    }

    @Override
    public void setFacing(Facing facing) {
        FACING.setState(this, facing);
    }

    @Override
    public Facing getFacing() {
        return FACING.getState(this);
    }

}
