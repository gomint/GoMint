package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sponge")
public class ItemSponge extends ItemStack< io.gomint.inventory.item.ItemSponge> implements io.gomint.inventory.item.ItemSponge {

    @Override
    public ItemType itemType() {
        return ItemType.SPONGE;
    }

}
