package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:speckled_melon", id = 382 )
 public class ItemGlisteringMelon extends ItemStack< io.gomint.inventory.item.ItemGlisteringMelon> implements io.gomint.inventory.item.ItemGlisteringMelon {



    @Override
    public ItemType itemType() {
        return ItemType.GLISTERING_MELON;
    }

}
