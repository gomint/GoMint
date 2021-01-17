package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:unlit_redstone_torch")
public class ItemRedstoneTorchInactive extends ItemStack<ItemRedstoneTorchInactive> {

    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE_TORCH_INACTIVE;
    }

}
