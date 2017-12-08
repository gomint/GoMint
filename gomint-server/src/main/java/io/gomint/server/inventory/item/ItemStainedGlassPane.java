package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 160 )
 public class ItemStainedGlassPane extends ItemStack implements io.gomint.inventory.item.ItemStainedGlassPane {

    // CHECKSTYLE:OFF
    public ItemStainedGlassPane( short data, int amount ) {
        super( 160, data, amount );
    }

    public ItemStainedGlassPane( short data, int amount, NBTTagCompound nbt ) {
        super( 160, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STAINED_GLASS_PANE;
    }

}