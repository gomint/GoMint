package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:item.iron_door", id = 71 )
public class ItemIronDoorBlock extends ItemStack<ItemIronDoorBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.IRON_DOOR;
    }

}
