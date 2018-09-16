package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 427 )
public class ItemSpruceDoor extends ItemStack implements io.gomint.inventory.item.ItemSpruceDoor {

    @Override
    public String getBlockId() {
        return "minecraft:spruce_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_DOOR;
    }

}
