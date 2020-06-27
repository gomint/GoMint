package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:acacia_door", id = 430 )
public class ItemAcaciaWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemAcaciaWoodDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_DOOR;
    }

}
