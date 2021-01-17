package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:tripwire_hook")
public class ItemTripwireHook extends ItemStack< io.gomint.inventory.item.ItemTripwireHook> implements io.gomint.inventory.item.ItemTripwireHook {

    @Override
    public ItemType itemType() {
        return ItemType.TRIPWIRE_HOOK;
    }

}
