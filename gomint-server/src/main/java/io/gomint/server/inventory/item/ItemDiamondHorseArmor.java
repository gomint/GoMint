package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 419 )
 public class ItemDiamondHorseArmor extends ItemStack implements io.gomint.inventory.item.ItemDiamondHorseArmor {

    // CHECKSTYLE:OFF
    public ItemDiamondHorseArmor( short data, int amount ) {
        super( 419, data, amount );
    }

    public ItemDiamondHorseArmor( short data, int amount, NBTTagCompound nbt ) {
        super( 419, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
