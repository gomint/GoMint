package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockCocoa;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Direction;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cocoa")
public class Cocoa extends Growable implements BlockCocoa {

    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"});
    private static final ProgressBlockState AGE = new ProgressBlockState(() -> new String[]{"age"}, 2, aVoid -> {
    });

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
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
        DIRECTION.setState(this, direction);
    }

    @Override
    public Direction getDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    protected void grow() {
        // Check for growth state
        if (AGE.getState(this) < 1f) {
            float growthDivider = getGrowthDivider();
            int random = ThreadLocalRandom.current().nextInt((int) ((25f / growthDivider) + 1));

            // Grow it
            if (random == 0) {
                // TODO: Some sort of growth event
                AGE.progress(this);
            }
        }
    }

}
