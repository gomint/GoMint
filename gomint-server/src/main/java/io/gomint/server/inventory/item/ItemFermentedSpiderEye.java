package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 376 )
 public class ItemFermentedSpiderEye extends ItemStack implements io.gomint.inventory.item.ItemFermentedSpiderEye {

    // CHECKSTYLE:OFF
    public ItemFermentedSpiderEye( short data, int amount ) {
        super( 376, data, amount );
    }

    public ItemFermentedSpiderEye( short data, int amount, NBTTagCompound nbt ) {
        super( 376, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FERMENTED_SPIDER_EYE;
    }

}