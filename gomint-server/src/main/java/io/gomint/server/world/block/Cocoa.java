package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Direction;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cocoa")
public class Cocoa extends Growable implements io.gomint.world.block.BlockCocoa {

    private final DirectionBlockState direction = new DirectionBlockState(this, () -> new String[]{"direction"});
    private final ProgressBlockState age = new ProgressBlockState(this, () -> new String[]{"age"},2, aVoid -> {});

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COCOA;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction.setState(direction);
    }

    @Override
    public Direction getDirection() {
        return this.direction.getState();
    }

}
