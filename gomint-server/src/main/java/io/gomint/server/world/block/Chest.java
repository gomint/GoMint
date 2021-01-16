package io.gomint.server.world.block;

import io.gomint.inventory.ChestInventory;
import io.gomint.inventory.Inventory;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockChest;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:chest")
public class Chest extends ChestBase<BlockChest> implements BlockChest {

    @Override
    public BlockType blockType() {
        return BlockType.CHEST;
    }

    @Override
    public BlockChest facing(Facing facing) {
        DIRECTION.setState(this, facing);
        return this;
    }

    @Override
    public Facing facing() {
        return DIRECTION.getState(this);
    }

    @Override
    public Inventory<ChestInventory> inventory() {
        return super.inventory();
    }

}
