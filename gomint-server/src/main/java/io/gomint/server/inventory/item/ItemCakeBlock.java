package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:item.cake" )
public class ItemCakeBlock extends ItemStack<ItemCakeBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.CAKE;
    }

}
