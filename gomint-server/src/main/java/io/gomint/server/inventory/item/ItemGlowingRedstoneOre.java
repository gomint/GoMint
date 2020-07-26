package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lit_redstone_ore", id = 74)
public class ItemGlowingRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemGlowingRedstoneOre {

    @Override
    public ItemType getItemType() {
        return ItemType.GLOWING_REDSTONE_ORE;
    }

}
