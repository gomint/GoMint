package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 324 )
public class ItemOakWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemOakWoodDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:wooden_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_DOOR;
    }

}
