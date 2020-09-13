package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockIce;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:ice")
public class Ice extends Block implements BlockIce {

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
    public BlockType getBlockType() {
        return BlockType.ICE;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        return new ArrayList<>();
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public io.gomint.world.block.Block performBreak(boolean creative) {
        Block below = this.world.getBlockAt(this.location.toBlockPosition().add(BlockPosition.DOWN));
        if (!creative || below.getBlockType() != BlockType.AIR) {
            return this.setBlockType(FlowingWater.class);
        }

        return super.performBreak(creative);
    }

}
