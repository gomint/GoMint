package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.CommandBlockTileEntity;
import io.gomint.world.block.BlockItemFrame;
import io.gomint.world.block.BlockType;

import io.gomint.server.entity.tileentity.ItemFrameTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:frame")
public class ItemFrame extends Block implements BlockItemFrame {

    @Override
    public String getBlockId() {
        return "minecraft:frame";
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(ItemFrameTileEntity.class, compound, this, this.items);
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ITEM_FRAME;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
