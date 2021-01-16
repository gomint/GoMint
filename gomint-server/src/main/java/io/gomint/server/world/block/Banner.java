package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;

import io.gomint.server.entity.tileentity.BannerTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Banner extends Block {

    @Override
    public float getBlastResistance() {
        return 5f;
    }

    @Override
    public long breakTime() {
        return 1500;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return this.tileEntities.construct(BannerTileEntity.class, compound, this, this.items);
    }
	
}
