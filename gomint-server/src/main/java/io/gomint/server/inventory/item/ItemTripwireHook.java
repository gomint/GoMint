package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:tripwire_hook", id = 131)
public class ItemTripwireHook extends ItemStack implements io.gomint.inventory.item.ItemTripwireHook {

    @Override
    public ItemType getItemType() {
        return ItemType.TRIPWIRE_HOOK;
    }

}
