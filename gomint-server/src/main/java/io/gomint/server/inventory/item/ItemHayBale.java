package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 170 )
 public class ItemHayBale extends ItemStack implements io.gomint.inventory.item.ItemHayBale {



    @Override
    public String getBlockId() {
        return "minecraft:hay_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.HAY_BALE;
    }

}
