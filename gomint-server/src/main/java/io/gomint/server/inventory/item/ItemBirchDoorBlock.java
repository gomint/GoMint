package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 194 )
public class ItemBirchDoorBlock extends ItemStack {



    @Override
    public ItemType getType() {
        return ItemType.BIRCH_DOOR_BLOCK;
    }

}
