package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:melon_seeds")
public class ItemMelonSeeds extends ItemStack< io.gomint.inventory.item.ItemMelonSeeds> implements io.gomint.inventory.item.ItemMelonSeeds {

    @Override
    public ItemType itemType() {
        return ItemType.MELON_SEEDS;
    }

}
