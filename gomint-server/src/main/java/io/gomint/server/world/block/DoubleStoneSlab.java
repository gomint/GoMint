package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockDoubleStoneSlab;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:double_stone_slab", def = true )
@RegisterInfo( sId = "minecraft:double_stone_slab2" )
@RegisterInfo( sId = "minecraft:double_stone_slab3" )
@RegisterInfo( sId = "minecraft:double_stone_slab4" )
@RegisterInfo(sId = "minecraft:blackstone_double_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_double_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_double_slab")
public class DoubleStoneSlab extends Block implements BlockDoubleStoneSlab {

    private static final String STONE_SLAB_ID = "minecraft:double_stone_slab";
    private static final String STONE_TYPE = "stone_slab_type";

    private static final String STONE_SLAB2_ID = "minecraft:double_stone_slab2";
    private static final String STONE_TYPE_2 = "stone_slab_type_2";

    private static final String STONE_SLAB3_ID = "minecraft:double_stone_slab3";
    private static final String STONE_TYPE_3 = "stone_slab_type_3";

    private static final String STONE_SLAB4_ID = "minecraft:double_stone_slab4";
    private static final String STONE_TYPE_4 = "stone_slab_type_4";

    public enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE(STONE_SLAB_ID, STONE_TYPE, "smooth_stone"),
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        // This is intended so that creative given "wooden" stone slabs don't break the server
        WOODEN("minecraft:double_wooden_slab", "wood_type", "oak"),
        COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "cobblestone"),
        BRICK(STONE_SLAB_ID, STONE_TYPE, "brick"),
        STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "stone_brick"),
        QUARTZ(STONE_SLAB_ID, STONE_TYPE, "quartz"),
        NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "nether_brick"),

        // Slab types 2
        RED_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "red_sandstone"),
        PURPUR(STONE_SLAB2_ID, STONE_TYPE_2, "purpur"),
        PRISMARINE_ROUGH(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_rough"),
        PRISMARINE_DARK(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_dark"),
        PRISMARINE_BRICK(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_brick"),
        MOSSY_COBBLESTONE(STONE_SLAB2_ID, STONE_TYPE_2, "mossy_cobblestone"),
        SMOOTH_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "smooth_sandstone"),
        RED_NETHER_BRICK(STONE_SLAB2_ID, STONE_TYPE_2, "red_nether_brick"),

        // Slab types 3
        END_STONE_BRICK(STONE_SLAB3_ID, STONE_TYPE_3, "end_stone_brick"),
        SMOOTH_RED_SANDSTONE(STONE_SLAB3_ID, STONE_TYPE_3, "smooth_red_sandstone"),
        POLISHED_ANDESITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_andesite"),
        ANDESITE(STONE_SLAB3_ID, STONE_TYPE_3, "andesite"),
        DIORITE(STONE_SLAB3_ID, STONE_TYPE_3, "diorite"),
        POLISHED_DIORITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_diorite"),
        GRANITE(STONE_SLAB3_ID, STONE_TYPE_3, "granite"),
        POLISHED_GRANITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_granite"),

        // Slab types 4
        MOSSY_STONE_BRICK(STONE_SLAB4_ID, STONE_TYPE_4, "mossy_stone_brick"),
        SMOOTH_QUARTZ(STONE_SLAB4_ID, STONE_TYPE_4, "smooth_quartz"),
        STONE(STONE_SLAB4_ID, STONE_TYPE_4, "stone"),
        CUT_SANDSTONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_sandstone"),
        CUT_RED_STONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_red_sandstone"),

        // Additional slabs (new ones)
        BLACKSTONE("minecraft:blackstone_double_slab", "", ""),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_brick_double_slab", "", ""),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_double_slab", "", "");

        private final String key;
        private final String value;
        private final String blockId;

        StoneTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }
    }

    private static final BooleanBlockState TOP = new BooleanBlockState( () -> new String[]{"top_slot_bit"} );

    private static final EnumBlockState<StoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        if (v == null) {
            return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
        }

        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                switch (value.key) {
                    case STONE_TYPE:
                        return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
                    case STONE_TYPE_2:
                        return new String[]{STONE_TYPE_2, STONE_TYPE, STONE_TYPE_3, STONE_TYPE_4};
                    case STONE_TYPE_3:
                        return new String[]{STONE_TYPE_3, STONE_TYPE_2, STONE_TYPE, STONE_TYPE_4};
                    case STONE_TYPE_4:
                        return new String[]{STONE_TYPE_4, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE};
                }
            }
        }

        return new String[]{STONE_TYPE, STONE_TYPE_2, STONE_TYPE_3, STONE_TYPE_4};
    }, StoneTypeMagic.values(), v -> v.value, v -> {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.DOUBLE_STONE_SLAB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public StoneType type() {
        switch (this.blockId()) {
            case "minecraft:blackstone_slab":
                return StoneType.BLACKSTONE;
            case "minecraft:polished_blackstone_double_slab":
                return StoneType.POLISHED_BLACKSTONE;
        }

        return StoneType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockDoubleStoneSlab type(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        this.blockId(newState.blockId);
        VARIANT.state(this, newState);
        return this;
    }

}
