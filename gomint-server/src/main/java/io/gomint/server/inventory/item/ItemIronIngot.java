package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:iron_ingot" )
 public class ItemIronIngot extends ItemStack< io.gomint.inventory.item.ItemIronIngot> implements io.gomint.inventory.item.ItemIronIngot {

    @Override
    public ItemType itemType() {
        return ItemType.IRON_INGOT;
    }

}
