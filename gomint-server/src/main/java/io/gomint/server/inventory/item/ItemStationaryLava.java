package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lava", id = 11)
public class ItemStationaryLava extends ItemStack implements io.gomint.inventory.item.ItemStationaryLava {

    @Override
    public ItemType getItemType() {
        return ItemType.STATIONARY_LAVA;
    }

}
