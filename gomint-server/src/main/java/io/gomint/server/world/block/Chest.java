package io.gomint.server.world.block;

import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockChest;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import java.util.List;

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
