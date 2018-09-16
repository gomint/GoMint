package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 130 )
 public class ItemEnderChest extends ItemStack implements io.gomint.inventory.item.ItemEnderChest {



    @Override
    public String getBlockId() {
        return "minecraft:ender_chest";
    }

    @Override
    public ItemType getType() {
        return ItemType.ENDER_CHEST;
    }

}
