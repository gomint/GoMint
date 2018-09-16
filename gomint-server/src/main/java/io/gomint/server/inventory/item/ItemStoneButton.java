package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 77 )
 public class ItemStoneButton extends ItemStack implements io.gomint.inventory.item.ItemStoneButton {



    @Override
    public String getBlockId() {
        return "minecraft:stone_button";
    }

    @Override
    public ItemType getType() {
        return ItemType.STONE_BUTTON;
    }

}
