package io.gomint.server.world.block;

import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trapped_chest" )
public class TrappedChest extends ChestBase implements io.gomint.world.block.BlockTrappedChest {

    @Override
    public BlockType getBlockType() {
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
