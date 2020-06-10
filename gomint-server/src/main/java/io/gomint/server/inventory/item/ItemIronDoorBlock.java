package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:iron_door", id = 71 )
public class ItemIronDoorBlock extends ItemStack {

    @Override
    public ItemType getType() {
        return ItemType.IRON_DOOR_BLOCK;
    }

}
