package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 161 )
 public class ItemAcaciaLeaves extends ItemStack implements io.gomint.inventory.item.ItemAcaciaLeaves {



    @Override
    public String getBlockId() {
        return "minecraft:leaves2";
    }

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_LEAVES;
    }

}
