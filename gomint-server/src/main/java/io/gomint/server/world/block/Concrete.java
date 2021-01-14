package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockColorBlockState;
import io.gomint.world.block.BlockConcrete;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:concrete" )
public class Concrete extends Block implements BlockConcrete {

    private static final BlockColorBlockState COLOR = new BlockColorBlockState(() -> new String[]{"color"});

    @Override
    public String getBlockId() {
        return "minecraft:concrete";
    }

    @Override
    public long getBreakTime() {
        return 2700;
    }

    @Override
    public float getBlastResistance() {
        return 9.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CONCRETE;
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
    public BlockColor color() {
        return COLOR.getState(this);
    }

    @Override
    public BlockConcrete color(BlockColor color ) {
        COLOR.setState( this, color );
        return this;
    }

}
