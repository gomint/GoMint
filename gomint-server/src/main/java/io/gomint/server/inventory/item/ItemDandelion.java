package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:yellow_flower", id = 37)
public class ItemDandelion extends ItemStack< io.gomint.inventory.item.ItemDandelion> implements io.gomint.inventory.item.ItemDandelion {

    @Override
    public ItemType itemType() {
        return ItemType.DANDELION;
    }

}
