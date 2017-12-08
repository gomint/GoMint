package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 295 )
 public class ItemSeeds extends ItemStack implements io.gomint.inventory.item.ItemSeeds {

    // CHECKSTYLE:OFF
    public ItemSeeds( short data, int amount ) {
        super( 295, data, amount );
    }

    public ItemSeeds( short data, int amount, NBTTagCompound nbt ) {
        super( 295, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SEEDS;
    }

}