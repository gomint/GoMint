package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_glazed_terracotta", id = 234)
public class ItemRedGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemRedGlazedTerracotta {

    @Override
    public ItemType getItemType() {
        return ItemType.RED_GLAZED_TERRACOTTA;
    }

}
