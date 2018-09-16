package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 169 )
 public class ItemSeaLantern extends ItemStack implements io.gomint.inventory.item.ItemSeaLantern {



    @Override
    public String getBlockId() {
        return "minecraft:seaLantern";
    }

    @Override
    public ItemType getType() {
        return ItemType.SEA_LANTERN;
    }

}
