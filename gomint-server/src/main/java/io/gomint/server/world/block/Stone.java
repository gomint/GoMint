package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCobblestone;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStone;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.BlockColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stone" )
public class Stone extends Block implements io.gomint.world.block.BlockStone {

    private final EnumBlockState<BlockStone.Type, String> variant = new EnumBlockState<>(this, v -> new String[]{"stone_type"}, BlockStone.Type.values(), e -> e.name().toLowerCase(), v -> BlockStone.Type.valueOf(v.toUpperCase()));

    @Override
    public String getBlockId() {
        return "minecraft:stone";
    }

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
        return new ArrayList<ItemStack>(){{
            add( ItemCobblestone.create( 1 ) );
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public void setStoneType(Type type) {
        this.variant.setState(type);
    }

    @Override
    public Type getStoneType() {
        return this.variant.getState();
    }

}
