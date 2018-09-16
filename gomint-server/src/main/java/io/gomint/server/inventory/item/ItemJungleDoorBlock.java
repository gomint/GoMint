package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 195 )
public class ItemJungleDoorBlock extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:jungle_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.JUNGLE_DOOR_BLOCK;
    }

}
