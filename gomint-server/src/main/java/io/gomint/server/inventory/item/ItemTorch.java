package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:torch")
public class ItemTorch extends ItemStack< io.gomint.inventory.item.ItemTorch> implements io.gomint.inventory.item.ItemTorch {

    @Override
    public ItemType itemType() {
        return ItemType.TORCH;
    }

}
