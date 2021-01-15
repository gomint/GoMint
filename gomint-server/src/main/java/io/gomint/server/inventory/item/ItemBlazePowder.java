package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:blaze_powder", id = 377 )
public class ItemBlazePowder extends ItemStack< io.gomint.inventory.item.ItemBlazePowder> implements io.gomint.inventory.item.ItemBlazePowder {

    @Override
    public ItemType itemType() {
        return ItemType.BLAZE_POWDER;
    }

}
