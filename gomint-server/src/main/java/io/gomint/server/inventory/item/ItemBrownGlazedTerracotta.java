package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 232 )
public class ItemBrownGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemBrownGlazedTerracotta {

    // CHECKSTYLE:OFF
    public ItemBrownGlazedTerracotta( short data, int amount ) {
        super( 232, data, amount );
    }

    public ItemBrownGlazedTerracotta( short data, int amount, NBTTagCompound nbt ) {
        super( 232, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BROWN_GLAZED_TERRACOTTA;
    }

}
