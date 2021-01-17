package io.gomint.server.world.block;

import io.gomint.inventory.ChestInventory;
import io.gomint.inventory.Inventory;
import io.gomint.world.block.BlockTrappedChest;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trapped_chest" )
public class TrappedChest extends ChestBase<BlockTrappedChest> implements BlockTrappedChest {

    @Override
    public BlockType blockType() {
        return BlockType.TRAPPED_CHEST;
    }

    @Override
    public Inventory<ChestInventory> inventory() {
        return super.inventory();
    }

    @Override
    public BlockTrappedChest facing(Facing facing) {
        DIRECTION.state(this, facing);
        return this;
    }

    @Override
    public Facing facing() {
        return DIRECTION.state(this);
    }

}
