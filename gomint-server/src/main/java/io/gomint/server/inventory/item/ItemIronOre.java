package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 15 )
 public class ItemIronOre extends ItemStack implements io.gomint.inventory.item.ItemIronOre {

    // CHECKSTYLE:OFF
    public ItemIronOre( short data, int amount ) {
        super( 15, data, amount );
    }

    public ItemIronOre( short data, int amount, NBTTagCompound nbt ) {
        super( 15, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
