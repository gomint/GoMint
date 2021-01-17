package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fermented_spider_eye" )
 public class ItemFermentedSpiderEye extends ItemStack< io.gomint.inventory.item.ItemFermentedSpiderEye> implements io.gomint.inventory.item.ItemFermentedSpiderEye {



    @Override
    public ItemType itemType() {
        return ItemType.FERMENTED_SPIDER_EYE;
    }

}
