package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:jungle_fence_gate", id = 185)
public class ItemJungleWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemJungleWoodFenceGate {

    @Override
    public ItemType getItemType() {
        return ItemType.JUNGLE_FENCE_GATE;
    }

}
