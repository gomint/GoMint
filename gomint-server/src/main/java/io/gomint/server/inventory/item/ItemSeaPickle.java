package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sea_pickle", id = -156 )
public class ItemSeaPickle extends ItemStack implements io.gomint.inventory.item.ItemSeaPickle {

    @Override
    public ItemType getType() {
        return ItemType.SEA_PICKLE;
    }

}
