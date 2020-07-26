package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:golden_rail", id = 27)
public class ItemPoweredRail extends ItemStack implements io.gomint.inventory.item.ItemPoweredRail {

    @Override
    public ItemType getItemType() {
        return ItemType.POWERED_RAIL;
    }

}
