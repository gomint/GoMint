/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPrismarineStairs;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.PrismarineType;

@RegisterInfo(sId = "minecraft:prismarine_stairs", def = true)
@RegisterInfo(sId = "minecraft:prismarine_bricks_stairs")
@RegisterInfo(sId = "minecraft:dark_prismarine_stairs")
public class PrismarineStairs extends Stairs<BlockPrismarineStairs> implements BlockPrismarineStairs {

    @Override
    public BlockType blockType() {
        return BlockType.PRISMARINE_STAIRS;
    }

    @Override
    public PrismarineType type() {
        switch (this.blockId()) {
            case "minecraft:prismarine_bricks_stairs":
                return PrismarineType.BRICK;
            case "minecraft:dark_prismarine_stairs":
                return PrismarineType.DARK;
        }

        return PrismarineType.NORMAL;
    }

    @Override
    public BlockPrismarineStairs type(PrismarineType type) {
        switch (type) {
            case DARK:
                this.blockId("minecraft:dark_prismarine_stairs");
                return this;
            case BRICK:
                this.blockId("minecraft:prismarine_bricks_stairs");
                return this;
        }

        this.blockId("minecraft:prismarine_stairs");
        return this;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public long breakTime() {
        return 7500;
    }

}
