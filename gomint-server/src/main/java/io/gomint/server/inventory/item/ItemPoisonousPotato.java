package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 394 )
 public class ItemPoisonousPotato extends ItemStack implements io.gomint.inventory.item.ItemPoisonousPotato {

    // CHECKSTYLE:OFF
    public ItemPoisonousPotato( short data, int amount ) {
        super( 394, data, amount );
    }

    public ItemPoisonousPotato( short data, int amount, NBTTagCompound nbt ) {
        super( 394, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
