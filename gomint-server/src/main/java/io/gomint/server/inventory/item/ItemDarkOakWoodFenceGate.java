package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dark_oak_fence_gate", id = 186)
public class ItemDarkOakWoodFenceGate extends ItemStack<io.gomint.inventory.item.ItemDarkOakWoodFenceGate> implements io.gomint.inventory.item.ItemDarkOakWoodFenceGate {

    @Override
    public ItemType itemType() {
        return ItemType.DARK_OAK_FENCE_GATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
