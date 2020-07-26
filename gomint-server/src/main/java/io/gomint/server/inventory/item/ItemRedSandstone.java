package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_sandstone", id = 179)
public class ItemRedSandstone extends ItemStack implements io.gomint.inventory.item.ItemRedSandstone {

    @Override
    public ItemType getItemType() {
        return ItemType.RED_SANDSTONE;
    }

}
