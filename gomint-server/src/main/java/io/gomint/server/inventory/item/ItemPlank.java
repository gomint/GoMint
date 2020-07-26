package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 5, sId = "minecraft:planks")
public class ItemPlank extends ItemStack implements io.gomint.inventory.item.ItemPlank {

    @Override
    public String getBlockId() {
        return "minecraft:planks";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.PLANK;
    }

}
