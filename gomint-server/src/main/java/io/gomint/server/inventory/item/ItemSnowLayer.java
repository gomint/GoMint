package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 78 )
 public class ItemSnowLayer extends ItemStack implements io.gomint.inventory.item.ItemSnowLayer {



    @Override
    public ItemType getType() {
        return ItemType.SNOW_LAYER;
    }

}
