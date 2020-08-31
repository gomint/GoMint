package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:spruce_door", id = 427, def = true)
@RegisterInfo(sId = "minecraft:item.spruce_door", id = 193)
public class ItemSpruceDoor extends ItemStack implements io.gomint.inventory.item.ItemSpruceDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SPRUCE_DOOR;
    }

}
