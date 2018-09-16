package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 248 )
 public class ItemUpdateGameBlockUpdate1 extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:info_update";
    }

    @Override
    public ItemType getType() {
        return ItemType.UPDATE_GAME_BLOCK_UPDATE1;
    }

}
