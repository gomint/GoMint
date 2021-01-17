package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:emerald_ore")
public class ItemEmeraldOre extends ItemStack< io.gomint.inventory.item.ItemEmeraldOre> implements io.gomint.inventory.item.ItemEmeraldOre {

    @Override
    public ItemType itemType() {
        return ItemType.EMERALD_ORE;
    }

}
