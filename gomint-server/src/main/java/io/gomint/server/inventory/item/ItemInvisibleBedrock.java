package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 95 )
 public class ItemInvisibleBedrock extends ItemStack implements io.gomint.inventory.item.ItemInvisibleBedrock {



    @Override
    public String getBlockId() {
        return "minecraft:invisibleBedrock";
    }

    @Override
    public ItemType getType() {
        return ItemType.INVISIBLE_BEDROCK;
    }

}
