package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:iron_door", id = 330)
public class ItemIronDoor extends ItemStack implements io.gomint.inventory.item.ItemIronDoor {

    @Override
    public ItemType getItemType() {
        return ItemType.IRON_DOOR;
    }

}
