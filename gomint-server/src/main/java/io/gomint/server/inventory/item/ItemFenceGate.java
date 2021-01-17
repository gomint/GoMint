package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fence_gate")
public class ItemFenceGate extends ItemStack<io.gomint.inventory.item.ItemFenceGate> implements io.gomint.inventory.item.ItemFenceGate {

    @Override
    public ItemType itemType() {
        return ItemType.FENCE_GATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
