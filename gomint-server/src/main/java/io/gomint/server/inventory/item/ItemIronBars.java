package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:iron_bars")
public class ItemIronBars extends ItemStack< io.gomint.inventory.item.ItemIronBars> implements io.gomint.inventory.item.ItemIronBars {

    @Override
    public ItemType itemType() {
        return ItemType.IRON_BARS;
    }

}
