package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 92 )
public class ItemCakeBlock extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:cake";
    }

    @Override
    public ItemType getType() {
        return ItemType.CAKE_BLOCK;
    }

}
