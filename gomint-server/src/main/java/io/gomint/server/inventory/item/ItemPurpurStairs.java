package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:purpur_stairs", id = 203)
public class ItemPurpurStairs extends ItemStack implements io.gomint.inventory.item.ItemPurpurStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.PURPUR_STAIRS;
    }

}
