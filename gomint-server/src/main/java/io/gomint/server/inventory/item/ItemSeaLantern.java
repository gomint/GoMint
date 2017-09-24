package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 169 )
 public class ItemSeaLantern extends ItemStack implements io.gomint.inventory.item.ItemSeaLantern {

    // CHECKSTYLE:OFF
    public ItemSeaLantern( short data, int amount ) {
        super( 169, data, amount );
    }

    public ItemSeaLantern( short data, int amount, NBTTagCompound nbt ) {
        super( 169, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
