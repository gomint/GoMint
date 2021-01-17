package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trident" )
public class ItemTrident extends ItemStack< io.gomint.inventory.item.ItemTrident> implements io.gomint.inventory.item.ItemTrident {

    @Override
    public ItemType itemType() {
        return ItemType.TRIDENT;
    }

}
