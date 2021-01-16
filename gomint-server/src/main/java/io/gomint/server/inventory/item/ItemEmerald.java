package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:emerald", id = 388 )
 public class ItemEmerald extends ItemStack< io.gomint.inventory.item.ItemEmerald> implements io.gomint.inventory.item.ItemEmerald {



    @Override
    public ItemType itemType() {
        return ItemType.EMERALD;
    }

}
