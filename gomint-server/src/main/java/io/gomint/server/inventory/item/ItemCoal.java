package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemBurnable;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 263 )
public class ItemCoal extends ItemStack implements io.gomint.inventory.item.ItemCoal, ItemBurnable {

    @Override
    public ItemType getType() {
        return ItemType.COAL;
    }

    @Override
    public long getBurnTime() {
        return 80000;
    }

}
