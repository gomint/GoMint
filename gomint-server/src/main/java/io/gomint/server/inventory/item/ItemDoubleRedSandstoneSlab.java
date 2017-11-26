package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 181 )
 public class ItemDoubleRedSandstoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleRedSandstoneSlab {

    // CHECKSTYLE:OFF
    public ItemDoubleRedSandstoneSlab( short data, int amount ) {
        super( 181, data, amount );
    }

    public ItemDoubleRedSandstoneSlab( short data, int amount, NBTTagCompound nbt ) {
        super( 181, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.DOUBLE_RED_SANDSTONE_SLAB;
    }

}