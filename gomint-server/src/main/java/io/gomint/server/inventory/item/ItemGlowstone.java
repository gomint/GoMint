package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 89 )
public class ItemGlowstone extends ItemStack implements io.gomint.inventory.item.ItemGlowstone {

    // CHECKSTYLE:OFF
    public ItemGlowstone( short data, int amount ) {
        super( 89, data, amount );
    }

    public ItemGlowstone( short data, int amount, NBTTagCompound nbt ) {
        super( 89, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GLOWSTONE;
    }

}
