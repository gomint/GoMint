package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dark_oak_door", id = 197)
public class ItemDarkOakDoorBlock extends ItemStack {

    @Override
    public ItemType getType() {
        return ItemType.DARK_OAK_DOOR_BLOCK;
    }

}
