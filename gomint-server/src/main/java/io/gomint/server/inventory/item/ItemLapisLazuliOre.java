package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lapis_ore", id = 21)
public class ItemLapisLazuliOre extends ItemStack implements io.gomint.inventory.item.ItemLapisLazuliOre {

    @Override
    public ItemType getItemType() {
        return ItemType.LAPIS_LAZULI_ORE;
    }

}
