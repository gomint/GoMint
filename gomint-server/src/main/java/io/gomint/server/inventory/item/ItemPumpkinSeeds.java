package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin_seeds", id = 361 )
public class ItemPumpkinSeeds extends ItemStack implements io.gomint.inventory.item.ItemPumpkinSeeds {

    @Override
    public ItemType getItemType() {
        return ItemType.PUMPKIN_SEEDS;
    }
}
