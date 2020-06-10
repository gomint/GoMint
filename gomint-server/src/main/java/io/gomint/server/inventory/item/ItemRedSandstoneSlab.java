package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_slab2", id = 182)
public class ItemRedSandstoneSlab extends ItemStack implements io.gomint.inventory.item.ItemRedSandstoneSlab {

    @Override
    public ItemType getType() {
        return ItemType.RED_SANDSTONE_SLAB;
    }

}
