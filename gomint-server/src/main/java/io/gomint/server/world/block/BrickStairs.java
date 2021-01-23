/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBrickStairs;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:brick_stairs")
public class BrickStairs extends Stairs<BlockBrickStairs> implements BlockBrickStairs {

    @Override
    public BlockType blockType() {
        return BlockType.BRICK_STAIRS;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public long breakTime() {
        return 10000;
    }

}
