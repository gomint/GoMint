package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 105 )
 public class ItemMelonStem extends ItemStack implements io.gomint.inventory.item.ItemMelonStem {

    // CHECKSTYLE:OFF
    public ItemMelonStem( short data, int amount ) {
        super( 105, data, amount );
    }

    public ItemMelonStem( short data, int amount, NBTTagCompound nbt ) {
        super( 105, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.MELON_STEM;
    }

}