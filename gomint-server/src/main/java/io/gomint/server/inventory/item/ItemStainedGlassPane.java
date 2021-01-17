package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stained_glass_pane")
public class ItemStainedGlassPane extends ItemStack< io.gomint.inventory.item.ItemStainedGlassPane> implements io.gomint.inventory.item.ItemStainedGlassPane {

    @Override
    public ItemType itemType() {
        return ItemType.STAINED_GLASS_PANE;
    }

}
