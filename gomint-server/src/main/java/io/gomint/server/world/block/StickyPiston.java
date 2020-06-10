package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sticky_piston")
public class StickyPiston extends Block implements io.gomint.world.block.BlockStickyPiston {

    private final BlockfaceBlockState facing = new BlockfaceBlockState(this, () -> new String[]{"facing_direction"});

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STICKY_PISTON;
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
    public void setFacing(Facing facing) {
        this.facing.setState(facing);
    }

    @Override
    public Facing getFacing() {
        return this.facing.getState();
    }

}
