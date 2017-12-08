package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 417 )
 public class ItemIronHorseArmor extends ItemStack implements io.gomint.inventory.item.ItemIronHorseArmor {

    // CHECKSTYLE:OFF
    public ItemIronHorseArmor( short data, int amount ) {
        super( 417, data, amount );
    }

    public ItemIronHorseArmor( short data, int amount, NBTTagCompound nbt ) {
        super( 417, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.IRON_HORSE_ARMOR;
    }

}