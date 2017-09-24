package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 322 )
 public class ItemGoldenApple extends ItemStack implements io.gomint.inventory.item.ItemGoldenApple {

    // CHECKSTYLE:OFF
    public ItemGoldenApple( short data, int amount ) {
        super( 322, data, amount );
    }

    public ItemGoldenApple( short data, int amount, NBTTagCompound nbt ) {
        super( 322, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
