package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:spruce_fence_gate", id = 183)
public class ItemSpruceFenceGate extends ItemStack<io.gomint.inventory.item.ItemSpruceFenceGate> implements io.gomint.inventory.item.ItemSpruceFenceGate {

    @Override
    public ItemType itemType() {
        return ItemType.SPRUCE_FENCE_GATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
