package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 223 )
public class ItemLightBlueGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemLightBlueGlazedTerracotta {



    @Override
    public ItemType getType() {
        return ItemType.LIGHT_BLUE_GLAZED_TERRACOTTA;
    }

}
