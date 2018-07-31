package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 231 )
public class ItemBlueGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemBlueGlazedTerracotta {



    @Override
    public ItemType getType() {
        return ItemType.BLUE_GLAZED_TERRACOTTA;
    }

}
