package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dark_oak_fence_gate", id = 186)
public class ItemDarkOakWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemDarkOakWoodFenceGate {

    @Override
    public ItemType getItemType() {
        return ItemType.DARK_OAK_FENCE_GATE;
    }

}
