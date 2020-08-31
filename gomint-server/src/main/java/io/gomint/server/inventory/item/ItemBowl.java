package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bowl", id = 281 )
 public class ItemBowl extends ItemStack implements io.gomint.inventory.item.ItemBowl {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BOWL;
    }

}