package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Dirt;
import io.gomint.server.world.block.Farmland;
import io.gomint.server.world.block.GrassBlock;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;
import io.gomint.server.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 290 )
 public class ItemWoodenHoe extends ItemReduceTierWooden implements io.gomint.inventory.item.ItemWoodenHoe {

    // CHECKSTYLE:OFF
    public ItemWoodenHoe( short data, int amount ) {
        super( 290, data, amount );
    }

    public ItemWoodenHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 290, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    public boolean interact ( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if( entity instanceof EntityPlayer && (clickedBlock instanceof Dirt || clickedBlock instanceof GrassBlock) ) {
            clickedBlock.setType( Farmland.class );
            return true;
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_HOE;
    }

}
