package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:double_plant", id = 175)
public class ItemSunflower extends ItemStack implements io.gomint.inventory.item.ItemSunflower {

    @Override
    public ItemType getItemType() {
        return ItemType.SUNFLOWER;
    }

}
