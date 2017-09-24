package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 257 )
public class ItemIronPickaxe extends ItemTool implements io.gomint.inventory.item.ItemIronPickaxe {

    // CHECKSTYLE:OFF
    public ItemIronPickaxe( short data, int amount ) {
        super( 257, data, amount );
    }

    public ItemIronPickaxe( short data, int amount, NBTTagCompound nbt ) {
        super( 257, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
