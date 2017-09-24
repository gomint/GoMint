package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 57 )
 public class ItemDiamondBlock extends ItemStack implements io.gomint.inventory.item.ItemDiamondBlock {

    // CHECKSTYLE:OFF
    public ItemDiamondBlock( short data, int amount ) {
        super( 57, data, amount );
    }

    public ItemDiamondBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 57, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
