package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 407 )
 public class ItemMinecartWithTnt extends ItemStack implements io.gomint.inventory.item.ItemMinecartWithTnt {

    // CHECKSTYLE:OFF
    public ItemMinecartWithTnt( short data, int amount ) {
        super( 407, data, amount );
    }

    public ItemMinecartWithTnt( short data, int amount, NBTTagCompound nbt ) {
        super( 407, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
