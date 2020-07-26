package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemBanner;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wall_banner", id = 177)
public class ItemWallBanner extends ItemStack implements ItemBanner {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WALL_BANNER;
    }
}
