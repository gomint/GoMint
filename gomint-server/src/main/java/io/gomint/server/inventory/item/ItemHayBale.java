package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:hay_block")
public class ItemHayBale extends ItemStack< io.gomint.inventory.item.ItemHayBale> implements io.gomint.inventory.item.ItemHayBale {

    @Override
    public ItemType itemType() {
        return ItemType.HAY_BALE;
    }

}
