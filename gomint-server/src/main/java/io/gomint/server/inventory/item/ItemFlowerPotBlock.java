package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:flower_pot", id = 140)
public class ItemFlowerPotBlock extends ItemStack implements io.gomint.inventory.item.ItemFlowerPotBlock {

    @Override
    public ItemType getType() {
        return ItemType.FLOWER_POT_BLOCK;
    }

}
