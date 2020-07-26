package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:wooden_door", id = 324 )
public class ItemOakWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemOakWoodDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WOODEN_DOOR;
    }

}
