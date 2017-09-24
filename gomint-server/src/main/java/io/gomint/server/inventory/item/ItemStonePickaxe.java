package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 274 )
 public class ItemStonePickaxe extends ItemStack implements io.gomint.inventory.item.ItemStonePickaxe {

    // CHECKSTYLE:OFF
    public ItemStonePickaxe( short data, int amount ) {
        super( 274, data, amount );
    }

    public ItemStonePickaxe( short data, int amount, NBTTagCompound nbt ) {
        super( 274, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
