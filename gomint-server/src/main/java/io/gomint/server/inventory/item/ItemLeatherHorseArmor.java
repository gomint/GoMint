package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 416 )
 public class ItemLeatherHorseArmor extends ItemStack implements io.gomint.inventory.item.ItemLeatherHorseArmor {

    // CHECKSTYLE:OFF
    public ItemLeatherHorseArmor( short data, int amount ) {
        super( 416, data, amount );
    }

    public ItemLeatherHorseArmor( short data, int amount, NBTTagCompound nbt ) {
        super( 416, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LEATHER_HORSE_ARMOR;
    }

}