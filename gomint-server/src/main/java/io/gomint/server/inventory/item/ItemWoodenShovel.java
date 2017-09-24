package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 269 )
public class ItemWoodenShovel extends ItemTool implements io.gomint.inventory.item.ItemWoodenShovel {

    // CHECKSTYLE:OFF
    public ItemWoodenShovel( short data, int amount ) {
        super( 269, data, amount );
    }

    public ItemWoodenShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 269, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
