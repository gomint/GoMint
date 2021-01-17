package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:shears" )
public class ItemShears extends ItemStack< io.gomint.inventory.item.ItemShears> implements io.gomint.inventory.item.ItemShears {

    @Override
    public ItemType itemType() {
        return ItemType.SHEARS;
    }

    @Override
    public float divisor() {
        return 6;
    }

}
