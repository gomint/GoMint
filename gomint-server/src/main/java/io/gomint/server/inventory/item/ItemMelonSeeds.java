package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 362 )
 public class ItemMelonSeeds extends ItemStack implements io.gomint.inventory.item.ItemMelonSeeds {

    // CHECKSTYLE:OFF
    public ItemMelonSeeds( short data, int amount ) {
        super( 362, data, amount );
    }

    public ItemMelonSeeds( short data, int amount, NBTTagCompound nbt ) {
        super( 362, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.MELON_SEEDS;
    }

}