package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 270 )
public class ItemWoodenPickaxe extends ItemReduceTierWooden implements io.gomint.inventory.item.ItemWoodenPickaxe {



    @Override
    public ItemType getType() {
        return ItemType.WOODEN_PICKAXE;
    }

}
