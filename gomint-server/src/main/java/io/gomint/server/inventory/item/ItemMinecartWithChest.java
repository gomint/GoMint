package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chest_minecart" )
 public class ItemMinecartWithChest extends ItemStack< io.gomint.inventory.item.ItemMinecartWithChest> implements io.gomint.inventory.item.ItemMinecartWithChest {



    @Override
    public ItemType itemType() {
        return ItemType.MINECART_WITH_CHEST;
    }

}
