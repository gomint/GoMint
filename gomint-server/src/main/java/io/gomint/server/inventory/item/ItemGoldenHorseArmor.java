package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 418 )
 public class ItemGoldenHorseArmor extends ItemStack implements io.gomint.inventory.item.ItemGoldenHorseArmor {

    // CHECKSTYLE:OFF
    public ItemGoldenHorseArmor( short data, int amount ) {
        super( 418, data, amount );
    }

    public ItemGoldenHorseArmor( short data, int amount, NBTTagCompound nbt ) {
        super( 418, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
