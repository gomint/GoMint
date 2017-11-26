package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 31 )
 public class ItemTallGrass extends ItemStack implements io.gomint.inventory.item.ItemTallGrass {

    // CHECKSTYLE:OFF
    public ItemTallGrass( short data, int amount ) {
        super( 31, data, amount );
    }

    public ItemTallGrass( short data, int amount, NBTTagCompound nbt ) {
        super( 31, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.TALL_GRASS;
    }

}