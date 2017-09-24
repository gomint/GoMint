package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 97 )
 public class ItemMonsterEgg extends ItemStack implements io.gomint.inventory.item.ItemMonsterEgg {

    // CHECKSTYLE:OFF
    public ItemMonsterEgg( short data, int amount ) {
        super( 97, data, amount );
    }

    public ItemMonsterEgg( short data, int amount, NBTTagCompound nbt ) {
        super( 97, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
