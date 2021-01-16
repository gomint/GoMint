package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:leaves", id = 18)
public class ItemLeaves extends ItemStack< io.gomint.inventory.item.ItemLeaves> implements io.gomint.inventory.item.ItemLeaves {

    @Override
    public ItemType itemType() {
        return ItemType.LEAVES;
    }

}
