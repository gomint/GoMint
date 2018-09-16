package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 183 )
 public class ItemSpruceFenceGate extends ItemStack implements io.gomint.inventory.item.ItemSpruceFenceGate {



    @Override
    public String getBlockId() {
        return "minecraft:spruce_fence_gate";
    }

    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_FENCE_GATE;
    }

}
