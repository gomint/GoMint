package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:skull", id = 144)
public class ItemSkullBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.SKULL_BLOCK;
    }

}
