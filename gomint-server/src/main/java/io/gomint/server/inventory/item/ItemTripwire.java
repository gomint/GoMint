package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:tripWire", id = 132)
public class ItemTripwire extends ItemStack implements io.gomint.inventory.item.ItemTripwire {

    @Override
    public ItemType getItemType() {
        return ItemType.TRIPWIRE;
    }

}
