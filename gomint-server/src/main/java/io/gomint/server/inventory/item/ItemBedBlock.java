package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bed", id = 26)
public class ItemBedBlock extends ItemStack {

    @Override
    public ItemType getType() {
        return ItemType.BED_BLOCK;
    }

}
