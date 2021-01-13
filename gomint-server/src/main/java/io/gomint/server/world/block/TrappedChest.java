package io.gomint.server.world.block;

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
public class TrappedChest extends ChestBase implements BlockTrappedChest {

    @Override
    public BlockType blockType() {
        return BlockType.TRAPPED_CHEST;
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

    @Override
    public void setFacing(Facing facing) {
        DIRECTION.setState(this, facing);
    }

    @Override
    public Facing getFacing() {
        return DIRECTION.getState(this);
    }

}
