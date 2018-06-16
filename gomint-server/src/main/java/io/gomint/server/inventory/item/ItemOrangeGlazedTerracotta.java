package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 221 )
public class ItemOrangeGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemOrangeGlazedTerracotta {

    // CHECKSTYLE:OFF
    public ItemOrangeGlazedTerracotta( short data, int amount ) {
        super( 221, data, amount );
    }

    public ItemOrangeGlazedTerracotta( short data, int amount, NBTTagCompound nbt ) {
        super( 221, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ORANGE_GLAZED_TERRACOTTA;
    }

}
