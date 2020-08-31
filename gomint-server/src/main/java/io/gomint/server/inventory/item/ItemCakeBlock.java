package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:item.cake", id = 92 )
public class ItemCakeBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.CAKE;
    }

}
