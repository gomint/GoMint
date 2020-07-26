package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall", id = 139)
public class ItemCobblestoneWall extends ItemStack implements io.gomint.inventory.item.ItemCobblestoneWall {

    @Override
    public ItemType getItemType() {
        return ItemType.COBBLESTONE_WALL;
    }

}
