package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 220 )
public class ItemWhiteGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemWhiteGlazedTerracotta {

    // CHECKSTYLE:OFF
    public ItemWhiteGlazedTerracotta( short data, int amount ) {
        super( 220, data, amount );
    }

    public ItemWhiteGlazedTerracotta( short data, int amount, NBTTagCompound nbt ) {
        super( 220, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.WHITE_GLAZED_TERRACOTTA;
    }

}
