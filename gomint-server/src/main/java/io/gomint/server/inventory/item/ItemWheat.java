package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:wheat" )
public class ItemWheat extends ItemStack< io.gomint.inventory.item.ItemWheat> implements io.gomint.inventory.item.ItemWheat {

    @Override
    public ItemType itemType() {
        return ItemType.WHEAT;
    }

}
