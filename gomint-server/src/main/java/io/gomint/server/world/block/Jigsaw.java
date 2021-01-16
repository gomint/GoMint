/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
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
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        FACING.detectFromPlacement(this, entity, item, face);
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public BlockType blockType() {
        return BlockType.JIGSAW;
    }

    @Override
    public BlockJigsaw facing(Facing facing) {
        FACING.setState(this, facing);
        return this;
    }

    @Override
    public Facing facing() {
        return FACING.getState(this);
    }

}
