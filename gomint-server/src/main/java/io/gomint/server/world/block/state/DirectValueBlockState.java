package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.BlockFace;

import java.util.List;
import java.util.function.Predicate;

public class DirectValueBlockState extends BlockState<Short> {

    public DirectValueBlockState(Block block) {
        super(block);
    }

    public DirectValueBlockState(Block block, Predicate<List<BlockState>> predicate) {
        super(block, predicate);
    }

    public DirectValueBlockState(Block block, Predicate<List<BlockState>> predicate, int shift) {
        super(block, predicate, shift);
    }

    @Override
    protected int cap() {
        return 0;
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, BlockFace face, Block block, Block clickedBlock, Vector clickPosition) {
        this.setState((short) 0);
    }

    @Override
    protected void data(short data) {
        this.setState(data);
    }

    @Override
    protected short data() {
        return this.getState();
    }

}
