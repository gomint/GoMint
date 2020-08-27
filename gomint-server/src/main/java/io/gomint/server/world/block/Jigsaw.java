/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.world.block.BlockJigsaw;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

@RegisterInfo(sId = "minecraft:jigsaw")
public class Jigsaw extends Block implements BlockJigsaw {

    private static final BlockfaceFromPlayerBlockState FACING = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, true);

    @Override
    public float getBlastResistance() {
        return 3600000;
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        FACING.detectFromPlacement(this, entity, item, face, block, clickedBlock, clickVector);
        return super.calculatePlacementData(entity, item, face, block, clickedBlock, clickVector);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JIGSAW;
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
