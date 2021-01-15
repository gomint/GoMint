package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:redstone_lamp", id = 123)
public class ItemRedstoneLampInactive extends ItemStack<ItemRedstoneLampInactive> {

    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE_LAMP_INACTIVE;
    }

}
