package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 278 )
 public class ItemDiamondPickaxe extends ItemStack implements io.gomint.inventory.item.ItemDiamondPickaxe {

    // CHECKSTYLE:OFF
    public ItemDiamondPickaxe( short data, int amount ) {
        super( 278, data, amount );
    }

    public ItemDiamondPickaxe( short data, int amount, NBTTagCompound nbt ) {
        super( 278, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
