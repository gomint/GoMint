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
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
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
    public boolean burning() {
        return this.blockId().equals("minecraft:lit_smoker");
    }

    @Override
    public BlockSmoker burning(boolean burning) {
        if (burning) {
            this.blockId("minecraft:lit_smoker");
        } else {
            this.blockId("minecraft:smoker");
        }

        return this;
    }

    @Override
    public BlockSmoker facing(Facing facing) {
        FACING.state(this, facing);
        return this;
    }

    @Override
    public Facing facing() {
        return FACING.state(this);
    }

}
