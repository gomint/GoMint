package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:unpowered_repeater", id = 93)
public class ItemRedstoneRepeaterInactive extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.REDSTONE_REPEATER_INACTIVE;
    }

}
