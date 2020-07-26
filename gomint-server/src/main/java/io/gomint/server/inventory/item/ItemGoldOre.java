package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:gold_ore", id = 14)
public class ItemGoldOre extends ItemStack implements io.gomint.inventory.item.ItemGoldOre {

    @Override
    public ItemType getItemType() {
        return ItemType.GOLD_ORE;
    }

}
