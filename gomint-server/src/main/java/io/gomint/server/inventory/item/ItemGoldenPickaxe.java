package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 285 )
 public class ItemGoldenPickaxe extends ItemStack implements io.gomint.inventory.item.ItemGoldenPickaxe {

    // CHECKSTYLE:OFF
    public ItemGoldenPickaxe( short data, int amount ) {
        super( 285, data, amount );
    }

    public ItemGoldenPickaxe( short data, int amount, NBTTagCompound nbt ) {
        super( 285, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
