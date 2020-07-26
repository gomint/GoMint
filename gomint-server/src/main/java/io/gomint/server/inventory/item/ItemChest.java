package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:chest", id = 54)
public class ItemChest extends ItemStack implements io.gomint.inventory.item.ItemChest {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CHEST;
    }

}
