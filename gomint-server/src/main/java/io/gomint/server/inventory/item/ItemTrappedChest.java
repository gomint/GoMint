package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:trapped_chest", id = 146)
public class ItemTrappedChest extends ItemStack implements io.gomint.inventory.item.ItemTrappedChest {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TRAPPED_CHEST;
    }

}
