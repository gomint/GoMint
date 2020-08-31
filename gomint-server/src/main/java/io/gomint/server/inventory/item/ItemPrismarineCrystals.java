package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:prismarine_crystals", id = 422 )
 public class ItemPrismarineCrystals extends ItemStack implements io.gomint.inventory.item.ItemPrismarineCrystals {



    @Override
    public ItemType getItemType() {
        return ItemType.PRISMARINE_CRYSTALS;
    }

}