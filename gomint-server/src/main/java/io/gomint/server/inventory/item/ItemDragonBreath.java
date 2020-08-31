package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dragon_breath", id = 437 )
 public class ItemDragonBreath extends ItemStack implements io.gomint.inventory.item.ItemDragonBreath {



    @Override
    public ItemType getItemType() {
        return ItemType.DRAGON_BREATH;
    }

}