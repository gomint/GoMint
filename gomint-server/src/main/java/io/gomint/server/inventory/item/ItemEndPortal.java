package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:end_portal", id = 119)
public class ItemEndPortal extends ItemStack implements io.gomint.inventory.item.ItemEndPortal {

    @Override
    public ItemType getItemType() {
        return ItemType.END_PORTAL;
    }

}
