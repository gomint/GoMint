package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:gunpowder" )
 public class ItemGunpowder extends ItemStack< io.gomint.inventory.item.ItemGunpowder> implements io.gomint.inventory.item.ItemGunpowder {



    @Override
    public ItemType itemType() {
        return ItemType.GUNPOWDER;
    }

}
