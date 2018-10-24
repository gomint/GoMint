package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 369 )
public class ItemBlazeRod extends ItemStack implements io.gomint.inventory.item.ItemBlazeRod {

    @Override
    public ItemType getType() {
        return ItemType.BLAZE_ROD;
    }

    @Override
    public long getBurnTime() {
        return 120000;
    }

}
