package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:prismarine", id = 168)
public class ItemPrismarine extends ItemStack implements io.gomint.inventory.item.ItemPrismarine {

    @Override
    public ItemType getItemType() {
        return ItemType.PRISMARINE;
    }

}
