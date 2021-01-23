/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneWall;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.StoneType;

@RegisterInfo(sId = "minecraft:cobblestone_wall")
@RegisterInfo(sId = "minecraft:blackstone_wall")
@RegisterInfo(sId = "minecraft:polished_blackstone_wall")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_wall")
public class StoneWall extends Wall<BlockStoneWall> implements BlockStoneWall {

    private static final String STONE_SLAB_ID = "minecraft:cobblestone_wall";
    private static final String STONE_TYPE = "wall_block_type";

    private enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE("", "", ""),
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        BRICK("", "", ""),
        STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "stone_brick"),
        QUARTZ("", "", ""),
        NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "nether_brick"),

        // Slab types 2
        RED_SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "red_sandstone"),
        PURPUR("", "", ""),

        SMOOTH_SANDSTONE("", "", ""),
        RED_NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "red_nether_brick"),

        // Slab types 3
        END_STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "end_brick"),
        SMOOTH_RED_SANDSTONE("", "", ""),
        POLISHED_ANDESITE("", "", ""),
        ANDESITE(STONE_SLAB_ID, STONE_TYPE, "andesite"),
        DIORITE(STONE_SLAB_ID, STONE_TYPE, "diorite"),
        POLISHED_DIORITE("", "", ""),
        GRANITE(STONE_SLAB_ID, STONE_TYPE, "granite"),
        POLISHED_GRANITE("", "", ""),

        // Slab types 4
        MOSSY_STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "mossy_stone_brick"),
        SMOOTH_QUARTZ("", "", ""),
        STONE("", "", ""),

        // Additional slabs (new ones)
        BLACKSTONE("minecraft:blackstone_wall", "", ""),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_wall", "", ""),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_wall", "", "");

        private final String key;
        private final String value;
        private final String blockId;

        StoneTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }

    }

    private static final EnumBlockState<StoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{STONE_TYPE};
    }, StoneTypeMagic.values(), v -> v.value, v -> {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public BlockType blockType() {
        return BlockType.STONE_WALL;
    }

    @Override
    public StoneType type() {
        switch (this.blockId()) {
            case "minecraft:blackstone_wall":
                return StoneType.BLACKSTONE;
            case "minecraft:polished_blackstone_wall":
                return StoneType.POLISHED_BLACKSTONE;
            case "minecraft:polished_blackstone_brick_wall":
                return StoneType.POLISHED_BLACKSTONE_BRICK;
        }

        return StoneType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockStoneWall type(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        if (newState.blockId.isEmpty()) {
            return this;
        }

        this.blockId(newState.blockId);
        VARIANT.state(this, newState);
        return this;
    }

}
