package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.acacia_door", id = 196)
@RegisterInfo(sId = "minecraft:item.birch_door", id = 194)
@RegisterInfo(sId = "minecraft:item.wooden_door", id = 64)
@RegisterInfo(sId = "minecraft:item.crimson_door", id = -244)
@RegisterInfo(sId = "minecraft:item.warped_door", id = -245)
@RegisterInfo(sId = "minecraft:item.dark_oak_door", id = 197)
@RegisterInfo(sId = "minecraft:item.jungle_door", id = 195)
@RegisterInfo(sId = "minecraft:item.spruce_door", id = 193)
public class ItemWoodenDoorBlock extends ItemStack<ItemWoodenDoorBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_DOOR;
    }

}
