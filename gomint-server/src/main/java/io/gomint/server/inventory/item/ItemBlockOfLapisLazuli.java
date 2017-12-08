package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 22 )
 public class ItemBlockOfLapisLazuli extends ItemStack implements io.gomint.inventory.item.ItemBlockOfLapisLazuli {

    // CHECKSTYLE:OFF
    public ItemBlockOfLapisLazuli( short data, int amount ) {
        super( 22, data, amount );
    }

    public ItemBlockOfLapisLazuli( short data, int amount, NBTTagCompound nbt ) {
        super( 22, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LAPIS_LAZULI_BLOCK;
    }

}
