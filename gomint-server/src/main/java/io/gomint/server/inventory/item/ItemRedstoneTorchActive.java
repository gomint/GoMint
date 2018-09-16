package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 76 )
 public class ItemRedstoneTorchActive extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:redstone_torch";
    }

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_TORCH_ACTIVE;
    }

}
