package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:concrete")
public class ItemConcrete extends ItemStack< io.gomint.inventory.item.ItemConcrete> implements io.gomint.inventory.item.ItemConcrete {

    @Override
    public ItemType itemType() {
        return ItemType.CONCRETE;
    }

}
