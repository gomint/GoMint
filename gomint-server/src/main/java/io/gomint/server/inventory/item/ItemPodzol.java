package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:podzol")
public class ItemPodzol extends ItemStack< io.gomint.inventory.item.ItemPodzol> implements io.gomint.inventory.item.ItemPodzol {

    @Override
    public ItemType itemType() {
        return ItemType.PODZOL;
    }

}
