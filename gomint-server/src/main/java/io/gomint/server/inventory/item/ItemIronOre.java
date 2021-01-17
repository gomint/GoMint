package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:iron_ore")
public class ItemIronOre extends ItemStack< io.gomint.inventory.item.ItemIronOre> implements io.gomint.inventory.item.ItemIronOre {

    @Override
    public ItemType itemType() {
        return ItemType.IRON_ORE;
    }

}
