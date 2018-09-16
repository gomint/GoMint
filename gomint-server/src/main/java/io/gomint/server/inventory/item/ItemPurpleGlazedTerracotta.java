package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 219 )
public class ItemPurpleGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemPurpleGlazedTerracotta {



    @Override
    public String getBlockId() {
        return "minecraft:purple_glazed_terracotta";
    }

    @Override
    public ItemType getType() {
        return ItemType.PURPLE_GLAZED_TERRACOTTA;
    }

}
