package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 330 )
public class ItemIronDoor extends ItemStack implements io.gomint.inventory.item.ItemIronDoor {

    @Override
    public String getBlockId() {
        return "minecraft:iron_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.IRON_DOOR;
    }

}
