package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.tileentity.BannerTileEntity;
import io.gomint.server.entity.tileentity.LecternTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockLectern;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lectern" )
public class Lectern extends Block implements BlockLectern {

    @Override
    public String blockId() {
        return "minecraft:lectern";
    }

    @Override
    public long breakTime() {
        return 3750;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LECTERN;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(LecternTileEntity.class, compound, this, this.items);
    }

}
