package io.gomint.server.inventory.item;

import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTool extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemTool( int material, short data, int amount ) {
        super( material, data, amount );
    }

    public ItemTool( int material, short data, int amount, NBTTagCompound nbt ) {
        super( material, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
