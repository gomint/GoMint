package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:gold_ingot" )
 public class ItemGoldIngot extends ItemStack< io.gomint.inventory.item.ItemGoldIngot> implements io.gomint.inventory.item.ItemGoldIngot {



    @Override
    public ItemType itemType() {
        return ItemType.GOLD_INGOT;
    }

}
