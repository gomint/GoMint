/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockSandstoneDoubleSlab;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneSlabType;

@RegisterInfo( sId = "minecraft:double_stone_slab[stone_slab_type=sandstone]", def = true )
@RegisterInfo( sId = "minecraft:double_stone_slab2[stone_slab_type2=red_sandstone,smooth_sandstone]" )
@RegisterInfo( sId = "minecraft:double_stone_slab3[stone_slab_type3=smooth_red_sandstone]" )
public class SandstoneDoubleSlab extends Block implements BlockSandstoneDoubleSlab {

    private static final String STONE_SLAB_ID = "minecraft:double_stone_slab";
    private static final String STONE_TYPE = "stone_slab_type";

    private static final String STONE_SLAB2_ID = "minecraft:double_stone_slab2";
    private static final String STONE_TYPE_2 = "stone_slab_type_2";

    private static final String STONE_SLAB3_ID = "minecraft:double_stone_slab3";
    private static final String STONE_TYPE_3 = "stone_slab_type_3";

    private static final String STONE_SLAB4_ID = "minecraft:double_stone_slab4";
    private static final String STONE_TYPE_4 = "stone_slab_type_4";

    private enum SandstoneTypeMagic {
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        RED_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "red_sandstone"),
        SMOOTH_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "smooth_sandstone"),
        SMOOTH_RED_SANDSTONE(STONE_SLAB3_ID, STONE_TYPE_3, "smooth_red_sandstone"),
        CUT_SANDSTONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_sandstone"),
        CUT_RED_SANDSTONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_red_sandstone");

        private final String key;
        private final String value;
        private final String blockId;

        SandstoneTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }
    }

    private static final BooleanBlockState TOP = new BooleanBlockState( () -> new String[]{"top_slot_bit"} ); // This is stupid and shouldn't be calculated, this is only there because vanilla extends double slabs from slabs
    private static final EnumBlockState<SandstoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        if (v == null) {
            return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
        }

        for (SandstoneTypeMagic value : SandstoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                switch (value.key) {
                    case STONE_TYPE:
                        return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
                    case STONE_TYPE_2:
                        return new String[]{STONE_TYPE_2, STONE_TYPE, STONE_TYPE_3, STONE_TYPE_4};
                    case STONE_TYPE_3:
                        return new String[]{STONE_TYPE_3, STONE_TYPE_2, STONE_TYPE, STONE_TYPE_4};
                    case STONE_TYPE_4:
                        return new String[]{STONE_TYPE_4, STONE_TYPE_3, STONE_TYPE_2, STONE_TYPE};
                }
            }
        }

        return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
    }, SandstoneTypeMagic.values(), v -> v.value, v -> {
        for (SandstoneTypeMagic value : SandstoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public BlockType blockType() {
        return BlockType.SANDSTONE_DOUBLE_SLAB;
    }

    @Override
    public BlockSandstoneDoubleSlab color(Sandcolor color) {
        return this.change(color, this.type());
    }

    @Override
    public Sandcolor color() {
        return VARIANT.state(this).value.contains("red_") ? Sandcolor.RED : Sandcolor.NORMAL;
    }

    @Override
    public BlockSandstoneDoubleSlab type(SandstoneSlabType type) {
        return this.change(this.color(), type);
    }

    private BlockSandstoneDoubleSlab change(Sandcolor color, SandstoneSlabType type) {
        SandstoneTypeMagic newState = null;

        if (color == Sandcolor.RED) {
            switch (type) {
                case NORMAL:
                    newState = SandstoneTypeMagic.RED_SANDSTONE;
                    break;
                case SMOOTH:
                    newState = SandstoneTypeMagic.SMOOTH_RED_SANDSTONE;
                    break;
                case CUT:
                    newState = SandstoneTypeMagic.CUT_RED_SANDSTONE;
                    break;
            }
        } else {
            switch (type) {
                case NORMAL:
                    newState = SandstoneTypeMagic.SANDSTONE;
                    break;
                case SMOOTH:
                    newState = SandstoneTypeMagic.SMOOTH_SANDSTONE;
                    break;
                case CUT:
                    newState = SandstoneTypeMagic.CUT_SANDSTONE;
                    break;
            }
        }

        VARIANT.state(this, newState);
        this.blockId(newState.blockId);

        return this;
    }

    @Override
    public SandstoneSlabType type() {
        if (VARIANT.state(this).value.contains("smooth_")) {
            return SandstoneSlabType.SMOOTH;
        }

        if (VARIANT.state(this).value.contains("cut_")) {
            return SandstoneSlabType.CUT;
        }

        return SandstoneSlabType.NORMAL;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

}
