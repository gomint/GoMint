package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemBanner;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 176 )
public class ItemStandingBanner extends ItemStack implements ItemBanner {

    // CHECKSTYLE:OFF
    public ItemStandingBanner( short data, int amount ) {
        super( 176, data, amount );
    }

    public ItemStandingBanner( short data, int amount, NBTTagCompound nbt ) {
        super( 176, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STANDING_BANNER;
    }

}
