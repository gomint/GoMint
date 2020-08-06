package io.gomint.server.world.block;

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
public class Chest extends ChestBase implements BlockChest {

    @Override
    public BlockType getBlockType() {
        return BlockType.CHEST;
    }

    @Override
    public void setFacing(Facing facing) {
        DIRECTION.setState(this, facing);
    }

    @Override
    public Facing getFacing() {
        return DIRECTION.getState(this);
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

}
