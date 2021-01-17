package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:portal")
public class ItemPortal extends ItemStack< io.gomint.inventory.item.ItemPortal> implements io.gomint.inventory.item.ItemPortal {

    @Override
    public ItemType itemType() {
        return ItemType.PORTAL;
    }

}
