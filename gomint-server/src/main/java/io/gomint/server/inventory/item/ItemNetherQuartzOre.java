package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:quartz_ore")
public class ItemNetherQuartzOre extends ItemStack< io.gomint.inventory.item.ItemNetherQuartzOre> implements io.gomint.inventory.item.ItemNetherQuartzOre {

    @Override
    public ItemType itemType() {
        return ItemType.NETHER_QUARTZ_ORE;
    }

}
