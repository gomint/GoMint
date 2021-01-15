package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lead", id = 420 )
 public class ItemLead extends ItemStack< io.gomint.inventory.item.ItemLead> implements io.gomint.inventory.item.ItemLead {



    @Override
    public ItemType itemType() {
        return ItemType.LEAD;
    }

}
