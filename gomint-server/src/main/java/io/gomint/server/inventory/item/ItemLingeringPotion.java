package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 441 )
public class ItemLingeringPotion extends ItemStack implements io.gomint.inventory.item.ItemLingeringPotion {

    // CHECKSTYLE:OFF
    public ItemLingeringPotion( short data, int amount ) {
        super( 441, data, amount );
    }

    public ItemLingeringPotion( short data, int amount, NBTTagCompound nbt ) {
        super( 441, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LINGERING_POTION;
    }

}
