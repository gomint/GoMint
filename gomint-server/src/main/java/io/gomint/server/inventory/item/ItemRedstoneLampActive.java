package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lit_redstone_lamp")
public class ItemRedstoneLampActive extends ItemStack<ItemRedstoneLampActive> {

    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE_LAMP_ACTIVE;
    }

}
