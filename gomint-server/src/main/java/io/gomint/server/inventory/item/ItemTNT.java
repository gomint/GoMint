package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 46 )
public class ItemTNT extends ItemStack implements io.gomint.inventory.item.ItemTNT {

    // CHECKSTYLE:OFF
    public ItemTNT( short data, int amount ) {
        super( 46, data, amount );
    }

    public ItemTNT( short data, int amount, NBTTagCompound nbt ) {
        super( 46, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.TNT;
    }

}
