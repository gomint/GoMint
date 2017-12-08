package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 446 )
public class ItemBanner extends ItemStack implements io.gomint.inventory.item.ItemBanner {

    // CHECKSTYLE:OFF
    public ItemBanner( short data, int amount ) {
        super( 446, data, amount );
    }

    public ItemBanner( short data, int amount, NBTTagCompound nbt ) {
        super( 446, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BANNER;
    }

}
