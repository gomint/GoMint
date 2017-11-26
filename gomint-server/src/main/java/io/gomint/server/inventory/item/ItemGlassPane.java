package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 102 )
 public class ItemGlassPane extends ItemStack implements io.gomint.inventory.item.ItemGlassPane {

    // CHECKSTYLE:OFF
    public ItemGlassPane( short data, int amount ) {
        super( 102, data, amount );
    }

    public ItemGlassPane( short data, int amount, NBTTagCompound nbt ) {
        super( 102, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GLASS_PANE;
    }

}