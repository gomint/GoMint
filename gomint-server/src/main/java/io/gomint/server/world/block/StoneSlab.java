package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneSlab;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_slab", def = true)
@RegisterInfo(sId = "minecraft:stone_slab2")
@RegisterInfo(sId = "minecraft:stone_slab3")
@RegisterInfo(sId = "minecraft:stone_slab4")
@RegisterInfo(sId = "minecraft:blackstone_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_slab")
public class StoneSlab extends Slab implements BlockStoneSlab {

    private static final String STONE_SLAB_ID = "minecraft:stone_slab";
    private static final String STONE_TYPE = "stone_slab_type";

    private static final String STONE_SLAB2_ID = "minecraft:stone_slab2";
    private static final String STONE_TYPE_2 = "stone_slab_type_2";

    private static final String STONE_SLAB3_ID = "minecraft:stone_slab3";
    private static final String STONE_TYPE_3 = "stone_slab_type_3";

    private static final String STONE_SLAB4_ID = "minecraft:stone_slab4";
    private static final String STONE_TYPE_4 = "stone_slab_type_4";

    public enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE(STONE_SLAB_ID, STONE_TYPE, "smooth_stone"),
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        WOODEN("minecraft:wooden_slab", "wood_type", "oak"), // This is intended so that creative given "wooden" stone slabs don't break the server
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
        BLACKSTONE("minecraft:blackstone_slab", "", ""),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_slab", "", ""),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_slab", "", "");

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
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_SLAB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public StoneType getStoneType() {
        switch (this.getBlockId()) {
            case "minecraft:blackstone_slab":
                return StoneType.BLACKSTONE;
            case "minecraft:polished_blackstone_slab":
                return StoneType.POLISHED_BLACKSTONE;
            case "minecraft:polished_blackstone_brick_slab":
                return StoneType.POLISHED_BLACKSTONE_BRICK;
        }

        return StoneType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public void setStoneType(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        this.setBlockId(newState.blockId);
        VARIANT.setState(this, newState);
    }

    @Override
    public boolean canBeReplaced(ItemStack item) {
        return item.getItemType() == ItemType.STONE_SLAB;
    }

}
