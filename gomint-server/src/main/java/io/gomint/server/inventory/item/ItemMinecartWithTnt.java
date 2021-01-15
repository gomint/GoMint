package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:tnt_minecart", id = 407 )
 public class ItemMinecartWithTnt extends ItemStack< io.gomint.inventory.item.ItemMinecartWithTnt> implements io.gomint.inventory.item.ItemMinecartWithTnt {



    @Override
    public ItemType itemType() {
        return ItemType.MINECART_WITH_TNT;
    }

}
