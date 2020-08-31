package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:rabbit_foot", id = 414 )
 public class ItemRabbitFoot extends ItemStack implements io.gomint.inventory.item.ItemRabbitFoot {



    @Override
    public ItemType getItemType() {
        return ItemType.RABBIT_FOOT;
    }

}