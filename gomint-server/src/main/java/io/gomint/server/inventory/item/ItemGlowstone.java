package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:glowstone")
public class ItemGlowstone extends ItemStack< io.gomint.inventory.item.ItemGlowstone> implements io.gomint.inventory.item.ItemGlowstone {

    @Override
    public ItemType itemType() {
        return ItemType.GLOWSTONE;
    }

}
