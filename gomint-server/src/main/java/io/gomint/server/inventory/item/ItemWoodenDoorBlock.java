package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.acacia_door")
@RegisterInfo(sId = "minecraft:item.birch_door")
@RegisterInfo(sId = "minecraft:item.wooden_door")
@RegisterInfo(sId = "minecraft:item.crimson_door")
@RegisterInfo(sId = "minecraft:item.warped_door")
@RegisterInfo(sId = "minecraft:item.dark_oak_door")
@RegisterInfo(sId = "minecraft:item.jungle_door")
@RegisterInfo(sId = "minecraft:item.spruce_door")
public class ItemWoodenDoorBlock extends ItemStack<ItemWoodenDoorBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_DOOR;
    }

}
