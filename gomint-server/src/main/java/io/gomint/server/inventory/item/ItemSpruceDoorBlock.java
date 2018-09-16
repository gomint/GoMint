package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 193 )
public class ItemSpruceDoorBlock extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:spruce_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_DOOR_BLOCK;
    }

}
