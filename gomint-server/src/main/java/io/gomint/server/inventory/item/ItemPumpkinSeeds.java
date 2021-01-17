package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin_seeds" )
public class ItemPumpkinSeeds extends ItemStack< io.gomint.inventory.item.ItemPumpkinSeeds> implements io.gomint.inventory.item.ItemPumpkinSeeds {

    @Override
    public ItemType itemType() {
        return ItemType.PUMPKIN_SEEDS;
    }
}
