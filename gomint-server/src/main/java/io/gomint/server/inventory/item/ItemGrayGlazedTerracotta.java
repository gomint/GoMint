package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 227 )
public class ItemGrayGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemGrayGlazedTerracotta {



    @Override
    public ItemType getType() {
        return ItemType.GRAY_GLAZED_TERRACOTTA;
    }

}
