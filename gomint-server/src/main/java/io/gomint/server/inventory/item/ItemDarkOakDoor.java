package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 431 )
 public class ItemDarkOakDoor extends ItemStack implements io.gomint.inventory.item.ItemDarkOakDoor {

    // CHECKSTYLE:OFF
    public ItemDarkOakDoor( short data, int amount ) {
        super( 431, data, amount );
    }

    public ItemDarkOakDoor( short data, int amount, NBTTagCompound nbt ) {
        super( 431, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 197;
    }

}
