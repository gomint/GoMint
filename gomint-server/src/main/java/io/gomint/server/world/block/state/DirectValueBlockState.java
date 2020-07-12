package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

public class DirectValueBlockState<T> extends BlockState<T, T> {

    private final T defaultValue;

    public DirectValueBlockState(Block block, Supplier<String[]> key, T defaultValue) {
        super(block, v -> key.get());
        this.defaultValue = defaultValue;
    }

    @Override
    protected void calculateValueFromState(T state) {
        this.setValue(state);
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        this.setState(this.defaultValue);
    }

    @Override
    public T getState() {
        return this.getValue();
    }

}
