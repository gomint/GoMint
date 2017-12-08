package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemReduceTierIron extends ItemStack implements ItemReduceBreaktime {

    // CHECKSTYLE:OFF
    ItemReduceTierIron( int material, short data, int amount ) {
        super( material, data, amount );
    }

    ItemReduceTierIron( int material, short data, int amount, NBTTagCompound nbt ) {
        super( material, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public double getDivisor() {
        return 6;
    }

}
