package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.GrassBlock;
import io.gomint.server.world.block.GrassPath;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;
import io.gomint.server.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 269 )
public class ItemWoodenShovel extends ItemReduceTierWooden implements io.gomint.inventory.item.ItemWoodenShovel {

    // CHECKSTYLE:OFF
    public ItemWoodenShovel( short data, int amount ) {
        super( 269, data, amount );
    }

    public ItemWoodenShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 269, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    public boolean interact ( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if( entity instanceof EntityPlayer && clickedBlock instanceof GrassBlock ) {
            clickedBlock.setType( GrassPath.class );
            return true;
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_SHOVEL;
    }

}
