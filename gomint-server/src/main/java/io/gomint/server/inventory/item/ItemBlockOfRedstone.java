package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 152 )
 public class ItemBlockOfRedstone extends ItemStack implements io.gomint.inventory.item.ItemBlockOfRedstone {

    // CHECKSTYLE:OFF
    public ItemBlockOfRedstone( short data, int amount ) {
        super( 152, data, amount );
    }

    public ItemBlockOfRedstone( short data, int amount, NBTTagCompound nbt ) {
        super( 152, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_REDSTONE;
    }

}