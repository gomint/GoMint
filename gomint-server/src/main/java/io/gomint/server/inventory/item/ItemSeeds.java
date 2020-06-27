package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wheat_seeds", id = 295)
public class ItemSeeds extends ItemStack implements io.gomint.inventory.item.ItemSeeds {

    @Override
    public ItemType getType() {
        return ItemType.SEEDS;
    }
}
