package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:trapped_chest", id = 146)
public class ItemTrappedChest extends ItemStack< io.gomint.inventory.item.ItemTrappedChest> implements io.gomint.inventory.item.ItemTrappedChest {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.TRAPPED_CHEST;
    }

}
