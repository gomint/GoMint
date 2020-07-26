package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:iron_trapdoor", id = 167)
public class ItemIronTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemIronTrapdoor {

    @Override
    public ItemType getItemType() {
        return ItemType.IRON_TRAPDOOR;
    }

}
