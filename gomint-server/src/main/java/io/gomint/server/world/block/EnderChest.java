package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.EnderChestTileEntity;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 130 )
public class EnderChest extends Block implements io.gomint.world.block.BlockEnderChest {

    @Override
    public int getBlockId() {
        return 130;
    }

    @Override
    public long getBreakTime() {
        return 33750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        EnderChestTileEntity tileEntity = this.getTileEntity();
        if ( tileEntity != null ) {
            tileEntity.interact( entity, face, facePos, item );
            return true;
        }

        return false;
    }

    @Override
    public float getBlastResistance() {
        return 3000.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ENDER_CHEST;
    }

}