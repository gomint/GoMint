package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 91 )
public class ItemJackOLantern extends ItemStack implements io.gomint.inventory.item.ItemJackOLantern {

    // CHECKSTYLE:OFF
    public ItemJackOLantern( short data, int amount ) {
        super( 91, data, amount );
    }

    public ItemJackOLantern( short data, int amount, NBTTagCompound nbt ) {
        super( 91, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.JACK_O_LANTERN;
    }

}
