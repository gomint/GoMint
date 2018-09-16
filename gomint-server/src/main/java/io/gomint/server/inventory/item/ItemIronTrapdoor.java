package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 167 )
 public class ItemIronTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemIronTrapdoor {



    @Override
    public String getBlockId() {
        return "minecraft:iron_trapdoor";
    }

    @Override
    public ItemType getType() {
        return ItemType.IRON_TRAPDOOR;
    }

}
