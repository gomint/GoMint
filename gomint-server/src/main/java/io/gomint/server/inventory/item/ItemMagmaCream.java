package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:magma_cream" )
 public class ItemMagmaCream extends ItemStack< io.gomint.inventory.item.ItemMagmaCream> implements io.gomint.inventory.item.ItemMagmaCream {



    @Override
    public ItemType itemType() {
        return ItemType.MAGMA_CREAM;
    }

}
