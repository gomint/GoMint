package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 129 )
 public class ItemEmeraldOre extends ItemStack implements io.gomint.inventory.item.ItemEmeraldOre {



    @Override
    public String getBlockId() {
        return "minecraft:emerald_ore";
    }

    @Override
    public ItemType getType() {
        return ItemType.EMERALD_ORE;
    }

}
