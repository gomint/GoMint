package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:unlit_redstone_torch", id = 75)
public class ItemRedstoneTorchInactive extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.REDSTONE_TORCH_INACTIVE;
    }

}
