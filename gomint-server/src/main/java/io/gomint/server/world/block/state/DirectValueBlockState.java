package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

public class DirectValueBlockState<T> extends BlockState<T, T> {

    private final T defaultValue;

    public DirectValueBlockState(Supplier<String[]> key, T defaultValue) {
        super(v -> key.get());
        this.defaultValue = defaultValue;
    }

    @Override
    protected void calculateValueFromState(Block block, T state) {
        this.value(block, state);
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face) {
        this.state(newBlock, this.defaultValue);
    }

    @Override
    public T state(Block block) {
        return this.value(block);
    }

}
