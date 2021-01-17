package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fence")
public class ItemFence extends ItemStack<io.gomint.inventory.item.ItemFence> implements io.gomint.inventory.item.ItemFence {

    @Override
    public ItemType itemType() {
        return ItemType.FENCE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
