package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 244 )
 public class ItemBeetrootBlock extends ItemStack implements io.gomint.inventory.item.ItemBeetrootBlock {

    // CHECKSTYLE:OFF
    public ItemBeetrootBlock( short data, int amount ) {
        super( 244, data, amount );
    }

    public ItemBeetrootBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 244, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
