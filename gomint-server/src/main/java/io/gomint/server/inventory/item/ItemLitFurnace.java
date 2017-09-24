package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 62 )
 public class ItemLitFurnace extends ItemStack implements io.gomint.inventory.item.ItemLitFurnace {

    // CHECKSTYLE:OFF
    public ItemLitFurnace( short data, int amount ) {
        super( 62, data, amount );
    }

    public ItemLitFurnace( short data, int amount, NBTTagCompound nbt ) {
        super( 62, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
