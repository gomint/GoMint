package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 74 )
 public class ItemGlowingRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemGlowingRedstoneOre {

    // CHECKSTYLE:OFF
    public ItemGlowingRedstoneOre( short data, int amount ) {
        super( 74, data, amount );
    }

    public ItemGlowingRedstoneOre( short data, int amount, NBTTagCompound nbt ) {
        super( 74, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
