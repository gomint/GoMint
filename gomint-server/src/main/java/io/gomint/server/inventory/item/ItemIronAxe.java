package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 258 )
public class ItemIronAxe extends ItemTool implements io.gomint.inventory.item.ItemIronAxe {

    // CHECKSTYLE:OFF
    public ItemIronAxe( short data, int amount ) {
        super( 258, data, amount );
    }

    public ItemIronAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 258, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
