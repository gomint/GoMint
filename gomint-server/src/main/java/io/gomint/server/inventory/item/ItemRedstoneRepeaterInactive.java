package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:unpowered_repeater")
public class ItemRedstoneRepeaterInactive extends ItemStack<ItemRedstoneRepeaterInactive> {

    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE_REPEATER_INACTIVE;
    }

}
