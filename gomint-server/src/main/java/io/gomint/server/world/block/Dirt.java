package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStone;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDirt;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dirt" )
public class Dirt extends Block implements BlockDirt {

    private final EnumBlockState<BlockDirt.Type, String> variant = new EnumBlockState<>(this, () -> "dirt_type", BlockDirt.Type.values(), e -> e.name().toLowerCase(), v -> BlockDirt.Type.valueOf(v.toUpperCase()));

    @Override
    public String getBlockId() {
        return "minecraft:dirt";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.SHOVEL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DIRT;
    }


    @Override
    public void setDirtType(Type type) {
        this.variant.setState(type);
    }

    @Override
    public Type getDirtType() {
        return this.variant.getState();
    }

}
