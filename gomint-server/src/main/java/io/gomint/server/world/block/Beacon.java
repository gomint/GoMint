package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.tileentity.BeaconTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:beacon" )
public class Beacon extends Block implements io.gomint.world.block.BlockBeacon {

    @Override
    public String getBlockId() {
        return "minecraft:beacon";
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEACON;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        return new BeaconTileEntity( this );
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
