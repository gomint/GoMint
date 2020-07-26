package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:spruce_door", id = 193)
public class ItemSpruceDoorBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.SPRUCE_DOOR_BLOCK;
    }

}
