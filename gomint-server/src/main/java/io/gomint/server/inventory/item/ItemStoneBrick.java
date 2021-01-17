package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stonebrick")
public class ItemStoneBrick extends ItemStack< io.gomint.inventory.item.ItemStoneBrick> implements io.gomint.inventory.item.ItemStoneBrick {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK;
    }

}
