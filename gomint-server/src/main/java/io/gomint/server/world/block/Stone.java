package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCobblestone;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStone;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stone", def = true )
@RegisterInfo( sId = "minecraft:smooth_stone" )
public class Stone extends Block implements BlockStone {

    private static final EnumBlockState<BlockStone.Type, String> VARIANT = new EnumBlockState<>(v -> new String[]{"stone_type"}, BlockStone.Type.values(), e -> e.name().toLowerCase(), v -> BlockStone.Type.valueOf(v.toUpperCase()));

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>() {{
            add(ItemCobblestone.create(1));
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public void setStoneType(Type type) {
        if (type == Type.STONE_SMOOTH) {
            this.setBlockId("minecraft:smooth_stone");
            return;
        }

        this.setBlockId("minecraft:stone");
        VARIANT.setState(this,type);
    }

    @Override
    public Type getStoneType() {
        if ("minecraft:smooth_stone".equals(this.getBlockId())) {
            return Type.STONE_SMOOTH;
        }

        return VARIANT.getState(this);
    }

}
