package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 416 )
 public class ItemLeatherHorseArmor extends ItemStack implements io.gomint.inventory.item.ItemLeatherHorseArmor {



    @Override
    public ItemType getItemType() {
        return ItemType.LEATHER_HORSE_ARMOR;
    }

}
