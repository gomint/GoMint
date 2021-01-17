package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:chorus_flower")
public class ItemChorusFlower extends ItemStack< io.gomint.inventory.item.ItemChorusFlower> implements io.gomint.inventory.item.ItemChorusFlower {

    @Override
    public ItemType itemType() {
        return ItemType.CHORUS_FLOWER;
    }

}
