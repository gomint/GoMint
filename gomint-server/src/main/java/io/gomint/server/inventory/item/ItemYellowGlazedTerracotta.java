package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 224 )
public class ItemYellowGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemYellowGlazedTerracotta {



    @Override
    public ItemType getType() {
        return ItemType.YELLOW_GLAZED_TERRACOTTA;
    }

}
