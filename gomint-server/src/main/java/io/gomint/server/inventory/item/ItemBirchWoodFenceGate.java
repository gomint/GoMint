package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:birch_fence_gate", id = 184)
public class ItemBirchWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodFenceGate {

    @Override
    public ItemType getItemType() {
        return ItemType.BIRCH_FENCE_GATE;
    }

}
