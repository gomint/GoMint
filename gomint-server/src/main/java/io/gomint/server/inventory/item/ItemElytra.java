package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 444 )
public class ItemElytra extends ItemStack implements io.gomint.inventory.item.ItemElytra {

    // CHECKSTYLE:OFF
    public ItemElytra( short data, int amount ) {
        super( 444, data, amount );
    }

    public ItemElytra( short data, int amount, NBTTagCompound nbt ) {
        super( 444, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ELYTRA;
    }

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            ItemStack old = (ItemStack) entity.getArmorInventory().getChestplate();
            entity.getArmorInventory().setChestplate( this );
            entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), old );
        }

        return false;
    }

}
