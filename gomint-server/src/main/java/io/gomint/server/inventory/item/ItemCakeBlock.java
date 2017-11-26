package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 92 )
public class ItemCakeBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemCakeBlock( short data, int amount ) {
        super( 92, data, amount );
    }

    public ItemCakeBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 92, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CAKE_BLOCK;
    }

}