package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.PistonArmTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pistonArmCollision" )
public class PistonHead extends Block implements io.gomint.world.block.BlockPistonHead {

    @Override
    public String getBlockId() {
        return "minecraft:pistonArmCollision";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PISTON_HEAD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
