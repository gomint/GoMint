package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 246 )
 public class ItemGlowingObsidian extends ItemStack implements io.gomint.inventory.item.ItemGlowingObsidian {

    // CHECKSTYLE:OFF
    public ItemGlowingObsidian( short data, int amount ) {
        super( 246, data, amount );
    }

    public ItemGlowingObsidian( short data, int amount, NBTTagCompound nbt ) {
        super( 246, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GLOWING_OBSIDIAN;
    }

}