package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stick", id = 280 )
public class ItemStick extends ItemStack implements io.gomint.inventory.item.ItemStick {

    @Override
    public long getBurnTime() {
        return 5000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.STICK;
    }

}