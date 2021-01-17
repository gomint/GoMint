package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:nether_brick_fence")
public class ItemNetherBrickFence extends ItemStack< io.gomint.inventory.item.ItemNetherBrickFence> implements io.gomint.inventory.item.ItemNetherBrickFence {

    @Override
    public ItemType itemType() {
        return ItemType.NETHER_BRICK_FENCE;
    }

}
