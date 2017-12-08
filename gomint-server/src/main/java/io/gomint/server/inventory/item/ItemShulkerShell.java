package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 445 )
public class ItemShulkerShell extends ItemStack implements io.gomint.inventory.item.ItemShulkerShell {

    // CHECKSTYLE:OFF
    public ItemShulkerShell( short data, int amount ) {
        super( 445, data, amount );
    }

    public ItemShulkerShell( short data, int amount, NBTTagCompound nbt ) {
        super( 445, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SHULKER_SHELL;
    }

}
