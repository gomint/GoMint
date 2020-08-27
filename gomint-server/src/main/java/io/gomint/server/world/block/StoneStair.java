package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockStoneStair;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sandstone_stairs")
@RegisterInfo(sId = "minecraft:brick_stairs")
@RegisterInfo(sId = "minecraft:mossy_cobblestone_stairs")
@RegisterInfo(sId = "minecraft:red_nether_brick_stairs")
@RegisterInfo(sId = "minecraft:mossy_stone_brick_stairs")
@RegisterInfo(sId = "minecraft:polished_andesite_stairs")
@RegisterInfo(sId = "minecraft:prismarine_bricks_stairs")
@RegisterInfo(sId = "minecraft:end_brick_stairs")
@RegisterInfo(sId = "minecraft:quartz_stairs")
@RegisterInfo(sId = "minecraft:stone_stairs")
@RegisterInfo(sId = "minecraft:normal_stone_stairs")
@RegisterInfo(sId = "minecraft:stone_brick_stairs")
@RegisterInfo(sId = "minecraft:andesite_stairs")
@RegisterInfo(sId = "minecraft:granite_stairs")
@RegisterInfo(sId = "minecraft:diorite_stairs")
@RegisterInfo(sId = "minecraft:purpur_stairs")
@RegisterInfo(sId = "minecraft:smooth_sandstone_stairs")
@RegisterInfo(sId = "minecraft:prismarine_stairs")
@RegisterInfo(sId = "minecraft:dark_prismarine_stairs")
@RegisterInfo(sId = "minecraft:brick_stairs")
@RegisterInfo(sId = "minecraft:polished_diorite_stairs")
@RegisterInfo(sId = "minecraft:nether_brick_stairs")
@RegisterInfo(sId = "minecraft:polished_granite_stairs")
@RegisterInfo(sId = "minecraft:blackstone_stairs")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_stairs")
@RegisterInfo(sId = "minecraft:red_sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_red_sandstone_stairs")
@RegisterInfo(sId = "minecraft:smooth_quartz_stairs")
@RegisterInfo(sId = "minecraft:polished_blackstone_stairs")
public class StoneStair extends Stair implements BlockStoneStair {

    private enum StoneTypeMagic {

        SANDSTONE("minecraft:sandstone_stairs"),
        COBBLESTONE("minecraft:stone_stairs"),
        BRICK("minecraft:brick_stairs"),
        STONE_BRICK("minecraft:stone_brick_stairs"),
        NETHER_BRICK("minecraft:nether_brick_stairs"),
        QUARTZ("minecraft:quartz_stairs"),
        SMOOTH_STONE,

        PRISMARINE_BRICK("minecraft:prismarine_bricks_stairs"),
        PRISMARINE_ROUGH("minecraft:prismarine_stairs"),
        PRISMARINE_DARK("minecraft:dark_prismarine_stairs"),
        MOSSY_COBBLESTONE("minecraft:mossy_cobblestone_stairs"),
        RED_SANDSTONE("minecraft:red_sandstone_stairs"),
        SMOOTH_SANDSTONE("minecraft:smooth_sandstone_stairs"),
        PURPUR("minecraft:purpur_stairs"),
        RED_NETHER_BRICK("minecraft:red_nether_brick_stairs"),

        POLISHED_GRANITE("minecraft:polished_granite_stairs"),
        DIORITE("minecraft:diorite_stairs"),
        ANDESITE("minecraft:andesite_stairs"),
        POLISHED_DIORITE("minecraft:polished_diorite_stairs"),
        END_STONE_BRICK("minecraft:end_brick_stairs"),
        GRANITE("minecraft:granite_stairs"),
        POLISHED_ANDESITE("minecraft:polished_andesite_stairs"),
        SMOOTH_RED_SANDSTONE("minecraft:smooth_red_sandstone_stairs"),

        STONE("minecraft:normal_stone_stairs"),
        MOSSY_STONE_BRICK("minecraft:mossy_stone_brick_stairs"),
        SMOOTH_QUARTZ("minecraft:smooth_quartz_stairs"),
        CUT_RED_STONE,
        CUT_SANDSTONE,

        BLACKSTONE("minecraft:blackstone_stairs"),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_stairs"),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_stairs");

        private final String blockId;
        StoneTypeMagic() {
            this.blockId = null;
        }

        StoneTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    @Override
    public String getBlockId() {
        return "minecraft:brick_stairs";
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_STAIR;
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
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (this.getBlockId().equals(value.blockId)) {
                return StoneType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setStoneType(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        if (newState.blockId != null) {
            this.setBlockId(newState.blockId);
        }
    }

}
