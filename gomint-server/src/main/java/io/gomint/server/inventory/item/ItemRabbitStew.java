package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 413 )
 public class ItemRabbitStew extends ItemStack implements io.gomint.inventory.item.ItemRabbitStew {

    // CHECKSTYLE:OFF
    public ItemRabbitStew( short data, int amount ) {
        super( 413, data, amount );
    }

    public ItemRabbitStew( short data, int amount, NBTTagCompound nbt ) {
        super( 413, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
