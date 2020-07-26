package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:reserved6", id = 255)
public class ItemReserved6 extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.RESERVED6;
    }

}
