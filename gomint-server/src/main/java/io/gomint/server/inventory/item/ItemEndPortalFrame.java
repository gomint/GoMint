package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:end_portal_frame", id = 120)
public class ItemEndPortalFrame extends ItemStack implements io.gomint.inventory.item.ItemEndPortalFrame {

    @Override
    public ItemType getItemType() {
        return ItemType.END_PORTAL_FRAME;
    }

}
