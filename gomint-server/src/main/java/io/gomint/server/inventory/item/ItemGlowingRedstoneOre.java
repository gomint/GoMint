package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 74 )
 public class ItemGlowingRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemGlowingRedstoneOre {



    @Override
    public String getBlockId() {
        return "minecraft:lit_redstone_ore";
    }

    @Override
    public ItemType getType() {
        return ItemType.GLOWING_REDSTONE_ORE;
    }

}
