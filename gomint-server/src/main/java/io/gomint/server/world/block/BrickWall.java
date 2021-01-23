/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBrickWall;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBrickStairs;
import io.gomint.world.block.BlockBrickWall;
import io.gomint.world.block.BlockType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:cobblestone_wall[wall_block_type=brick]")
public class BrickWall extends Wall<BlockBrickWall> implements BlockBrickWall {

    @Override
    public BlockType blockType() {
        return BlockType.BRICK_WALL;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public long breakTime() {
        return 10000;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemBrickWall.create(1));
    }

}
