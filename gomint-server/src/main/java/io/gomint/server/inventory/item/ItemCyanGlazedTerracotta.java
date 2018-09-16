package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 229 )
public class ItemCyanGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemCyanGlazedTerracotta {



    @Override
    public String getBlockId() {
        return "minecraft:cyan_glazed_terracotta";
    }

    @Override
    public ItemType getType() {
        return ItemType.CYAN_GLAZED_TERRACOTTA;
    }

}
