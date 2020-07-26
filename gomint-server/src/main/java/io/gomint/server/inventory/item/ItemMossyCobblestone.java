package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:mossy_cobblestone", id = 48)
public class ItemMossyCobblestone extends ItemStack implements io.gomint.inventory.item.ItemMossyCobblestone {

    @Override
    public ItemType getItemType() {
        return ItemType.MOSSY_COBBLESTONE;
    }

}
