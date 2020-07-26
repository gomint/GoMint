package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:podzol", id = 243)
public class ItemPodzol extends ItemStack implements io.gomint.inventory.item.ItemPodzol {

    @Override
    public ItemType getItemType() {
        return ItemType.PODZOL;
    }

}
