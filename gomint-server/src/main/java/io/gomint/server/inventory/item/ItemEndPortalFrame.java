package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:end_portal_frame")
public class ItemEndPortalFrame extends ItemStack< io.gomint.inventory.item.ItemEndPortalFrame> implements io.gomint.inventory.item.ItemEndPortalFrame {

    @Override
    public ItemType itemType() {
        return ItemType.END_PORTAL_FRAME;
    }

}
