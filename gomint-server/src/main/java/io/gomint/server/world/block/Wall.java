package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockWall;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.StoneType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cobblestone_wall" )
public class Wall extends Block implements BlockWall {

    private static final String STONE_SLAB_ID = "minecraft:cobblestone_wall";
    private static final String STONE_TYPE = "wall_block_type";

    public enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE("", "", ""),
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "cobblestone"),
        BRICK("", "", ""),
        STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "stone_brick"),
        QUARTZ("", "", ""),
        NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "nether_brick"),

        // Slab types 2
        RED_SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "red_sandstone"),
        PURPUR("", "", ""),
        PRISMARINE_ROUGH(STONE_SLAB_ID, STONE_TYPE, "prismarine"),
        PRISMARINE_DARK("", "", ""),
        PRISMARINE_BRICK(STONE_SLAB_ID, STONE_TYPE, ""),
        MOSSY_COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "mossy_cobblestone"),
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
        CUT_SANDSTONE("", "", ""),
        CUT_RED_STONE("", "", ""),

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
        return BlockType.WALL;
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
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX() + 0.25f,
            this.location.getY(),
            this.location.getZ() + 0.25f ,
            this.location.getX() + 0.75f,
            this.location.getY() + 1,
            this.location.getZ() + 0.75f
        ) );
    }

    @Override
    public StoneType getStoneType() {
        switch (this.getBlockId()) {
            case "minecraft:blackstone_wall":
                return StoneType.BLACKSTONE;
            case "minecraft:polished_blackstone_wall":
                return StoneType.POLISHED_BLACKSTONE;
            case "minecraft:polished_blackstone_brick_wall":
                return StoneType.POLISHED_BLACKSTONE_BRICK;
        }

        return StoneType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public void setStoneType(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        if (newState.blockId.isEmpty()) {
            return;
        }

        this.setBlockId(newState.blockId);
        VARIANT.setState(this, newState);
    }



}
