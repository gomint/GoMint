package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:name_tag", id = 421 )
 public class ItemNameTag extends ItemStack implements io.gomint.inventory.item.ItemNameTag {



    @Override
    public ItemType getItemType() {
        return ItemType.NAME_TAG;
    }

}