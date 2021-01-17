package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:egg" )
 public class ItemEgg extends ItemStack< io.gomint.inventory.item.ItemEgg> implements io.gomint.inventory.item.ItemEgg {



    @Override
    public ItemType itemType() {
        return ItemType.EGG;
    }

}
