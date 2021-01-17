package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:jungle_fence_gate")
public class ItemJungleWoodFenceGate extends ItemStack<io.gomint.inventory.item.ItemJungleWoodFenceGate> implements io.gomint.inventory.item.ItemJungleWoodFenceGate {

    @Override
    public ItemType itemType() {
        return ItemType.JUNGLE_FENCE_GATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }


}
