package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Supplier;

@ToString
@EqualsAndHashCode(callSuper = false)
public class DirectValueBlockState<T> extends BlockState<T, T> {

    private final T defaultValue;

    public DirectValueBlockState(Supplier<String[]> key, T defaultValue) {
        super(v -> key.get());
        this.defaultValue = defaultValue;
    }

    @Override
    protected void calculateValueFromState(Block block, T state) {
        this.setValue(block, state);
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        this.setState(newBlock, this.defaultValue);
    }

    @Override
    public T getState( Block block ) {
        return this.getValue( block );
    }

}
