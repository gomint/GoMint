/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSandstoneStair;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.RoughnessType;
import io.gomint.world.block.data.Sandcolor;

@RegisterInfo(sId = "minecraft:red_sandstone_stairs")
@RegisterInfo(sId = "minecraft:sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_red_sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_sandstone_stairs")
public class SandstoneStairs extends Stairs<BlockSandstoneStair> implements BlockSandstoneStair {

    @Override
    public BlockType blockType() {
        return BlockType.SANDSTONE_STAIR;
    }

    @Override
    public BlockSandstoneStair color(Sandcolor color) {
        return this.change(color, this.type());
    }

    @Override
    public Sandcolor color() {
        return this.blockId().contains("red_") ? Sandcolor.RED : Sandcolor.NORMAL;
    }

    @Override
    public BlockSandstoneStair type(RoughnessType type) {
        return this.change(this.color(), type);
    }

    private BlockSandstoneStair change(Sandcolor color, RoughnessType type) {
        if (color == Sandcolor.RED && type == RoughnessType.SMOOTH) {
            this.blockId("minecraft:smooth_red_sandstone_stairs");
        } else if (color == Sandcolor.RED && type == RoughnessType.NORMAL) {
            this.blockId("minecraft:red_sandstone_stairs");
        } else if (color == Sandcolor.NORMAL && type == RoughnessType.SMOOTH) {
            this.blockId("minecraft:smooth_sandstone_stairs");
        } else {
            this.blockId("minecraft:sandstone_stairs");
        }

        return this;
    }

    @Override
    public RoughnessType type() {
        return this.blockId().contains("smooth_") ? RoughnessType.SMOOTH : RoughnessType.NORMAL;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public long breakTime() {
        return 3000;
    }

}
