package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stonebrick", id = 98)
public class ItemStoneBrick extends ItemStack implements io.gomint.inventory.item.ItemStoneBrick {

    @Override
    public ItemType getItemType() {
        return ItemType.STONE_BRICK;
    }

}
