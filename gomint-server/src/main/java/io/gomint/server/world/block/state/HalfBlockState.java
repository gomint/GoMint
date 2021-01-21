package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

public class HalfBlockState extends BooleanBlockState{
    public HalfBlockState(Supplier<String[]> key) {
        super(key);
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face, Vector clickVector) {
        if (face==Facing.UP) {
            this.state(newBlock, false);
            return;
        }
        if (face==Facing.DOWN) {
            this.state(newBlock, true);
            return;
        }
        if (clickVector.y() > 0.5)
            this.state(newBlock, true);
        else
            this.state(newBlock, false);
    }
}
