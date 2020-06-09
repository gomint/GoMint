package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneSlab;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.StoneType;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:double_stone_slab", def = true )
@RegisterInfo( sId = "minecraft:double_stone_slab2" )
@RegisterInfo( sId = "minecraft:double_stone_slab3" )
@RegisterInfo( sId = "minecraft:double_stone_slab4")
public class StoneSlab extends Slab implements BlockStoneSlab {

    private static final String STONE_SLAB_ID = "minecraft:double_stone_slab";
    private static final String STONE_TYPE = "stone_slab_type";

    private static final String STONE_SLAB2_ID = "minecraft:double_stone_slab2";
    private static final String STONE_TYPE_2 = "stone_slab_type_2";

    private static final String STONE_SLAB3_ID = "minecraft:double_stone_slab3";
    private static final String STONE_TYPE_3 = "stone_slab_type_3";

    private static final String STONE_SLAB4_ID = "minecraft:double_stone_slab4";
    private static final String STONE_TYPE_4 = "stone_slab_type_4";

    @Getter
    public enum StoneTypeMagic {

        // Slab types 1
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE,"sandstone"),
        COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "cobblestone"),
        BRICK(STONE_SLAB_ID, STONE_TYPE, "brick"),
        STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "stone_brick"),
        NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "nether_brick"),
        QUARTZ(STONE_SLAB_ID, STONE_TYPE, "quartz"),
        SMOOTH_STONE(STONE_SLAB_ID, STONE_TYPE, "smooth_stone"),

        // Slab types 2
        PRISMARINE_BRICK(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_brick"),
        PRISMARINE_ROUGH(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_rough"),
        PRISMARINE_DARK(STONE_SLAB2_ID, STONE_TYPE_2, "prismarine_dark"),
        MOSSY_COBBLESTONE(STONE_SLAB2_ID, STONE_TYPE_2, "mossy_cobblestone"),
        RED_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "red_sandstone"),
        SMOOTH_SANDSTONE(STONE_SLAB2_ID, STONE_TYPE_2, "smooth_sandstone"),
        PURPUR(STONE_SLAB2_ID, STONE_TYPE_2, "purpur"),
        RED_NETHER_BRICK(STONE_SLAB2_ID, STONE_TYPE_2, "red_nether_brick"),

        // Slab types 3
        POLISHED_GRANITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_granite"),
        DIORITE(STONE_SLAB3_ID, STONE_TYPE_3, "diorite"),
        ANDESITE(STONE_SLAB3_ID, STONE_TYPE_3, "andesite"),
        POLISHED_DIORITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_diorite"),
        END_STONE_BRICK(STONE_SLAB3_ID, STONE_TYPE_3, "end_stone_brick"),
        GRANITE(STONE_SLAB3_ID, STONE_TYPE_3, "granite"),
        POLISHED_ANDESITE(STONE_SLAB3_ID, STONE_TYPE_3, "polished_andesite"),
        SMOOTH_RED_SANDSTONE(STONE_SLAB3_ID, STONE_TYPE_3, "smooth_red_sandstone"),

        // Slab types 4
        STONE(STONE_SLAB4_ID, STONE_TYPE_4, "stone"),
        MOSSY_STONE_BRICK(STONE_SLAB4_ID, STONE_TYPE_4, "mossy_stone_brick"),
        SMOOTH_QUARTZ(STONE_SLAB4_ID, STONE_TYPE_4, "smooth_quartz"),
        CUT_RED_STONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_red_sandstone"),
        CUT_SANDSTONE(STONE_SLAB4_ID, STONE_TYPE_4, "cut_sandstone");

        private final String key;
        private final String value;
        private final String blockId;

        StoneTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }
    }

    private final EnumBlockState<StoneTypeMagic, String> variant = new EnumBlockState<>(this, () -> {
        return this.variant.getState().getKey();
    }, StoneTypeMagic.values(), StoneTypeMagic::getValue);

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
    public BlockType getType() {
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
        return StoneType.valueOf(this.variant.getState().name());
    }

    @Override
    public void setStoneType( StoneType stoneType ) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        this.setBlockId(newState.getBlockId());
        this.variant.setState(newState);
    }

}
