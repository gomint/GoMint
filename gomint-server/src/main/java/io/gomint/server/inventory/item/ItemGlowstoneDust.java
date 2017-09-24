package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 348 )
 public class ItemGlowstoneDust extends ItemStack implements io.gomint.inventory.item.ItemGlowstoneDust {

    // CHECKSTYLE:OFF
    public ItemGlowstoneDust( short data, int amount ) {
        super( 348, data, amount );
    }

    public ItemGlowstoneDust( short data, int amount, NBTTagCompound nbt ) {
        super( 348, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
