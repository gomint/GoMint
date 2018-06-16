package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 352 )
 public class ItemBone extends ItemStack implements io.gomint.inventory.item.ItemBone {

    // CHECKSTYLE:OFF
    public ItemBone( short data, int amount ) {
        super( 352, data, amount );
    }

    public ItemBone( short data, int amount, NBTTagCompound nbt ) {
        super( 352, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BONE;
    }

}