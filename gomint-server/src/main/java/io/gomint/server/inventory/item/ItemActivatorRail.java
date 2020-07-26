package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:activator_rail", id = 126)
public class ItemActivatorRail extends ItemStack implements io.gomint.inventory.item.ItemActivatorRail {

    @Override
    public ItemType getItemType() {
        return ItemType.ACTIVATOR_RAIL;
    }

}
