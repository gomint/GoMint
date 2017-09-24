package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 256 )
public class ItemIronShovel extends ItemTool implements io.gomint.inventory.item.ItemIronShovel {

    // CHECKSTYLE:OFF
    public ItemIronShovel( short data, int amount ) {
        super( 256, data, amount );
    }

    public ItemIronShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 256, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
