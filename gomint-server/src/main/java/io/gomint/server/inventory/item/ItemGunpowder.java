package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:gunpowder", id = 289 )
 public class ItemGunpowder extends ItemStack implements io.gomint.inventory.item.ItemGunpowder {



    @Override
    public ItemType getItemType() {
        return ItemType.GUNPOWDER;
    }

}