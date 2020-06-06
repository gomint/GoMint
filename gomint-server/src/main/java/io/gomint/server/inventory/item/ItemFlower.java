package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 38, sId = "minecraft:red_flower", def = true)
@RegisterInfo(id = -216, sId = "minecraft:wither_rose")
public class ItemFlower extends ItemStack implements io.gomint.inventory.item.ItemFlower {

    @Override
    public String getBlockId() {
        if (this.getMaterial() == 38) {
            return "minecraft:red_flower";
        } else {
            return "minecraft:wither_rose";
        }
    }

    @Override
    public ItemType getType() {
        return ItemType.FLOWER;
    }

}
