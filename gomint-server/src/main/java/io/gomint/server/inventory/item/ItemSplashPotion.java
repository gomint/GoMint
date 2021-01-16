package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:splash_potion", id = 438 )
 public class ItemSplashPotion extends ItemStack< io.gomint.inventory.item.ItemSplashPotion> implements io.gomint.inventory.item.ItemSplashPotion {



    @Override
    public ItemType itemType() {
        return ItemType.SPLASH_POTION;
    }

}
