package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:acacia_fence_gate", id = 187 )
public class ItemAcaciaWoodFenceGate extends ItemStack<io.gomint.inventory.item.ItemAcaciaWoodFenceGate> implements io.gomint.inventory.item.ItemAcaciaWoodFenceGate {

    @Override
    public ItemType itemType() {
        return ItemType.ACACIA_FENCE_GATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
