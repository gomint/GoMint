package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bone")
public class ItemBone extends ItemStack< io.gomint.inventory.item.ItemBone> implements io.gomint.inventory.item.ItemBone {

    @Override
    public ItemType itemType() {
        return ItemType.BONE;
    }

}
