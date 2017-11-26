package io.gomint.server.inventory.item;

import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemArmor extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemArmor( int material, short data, int amount ) {
        super( material, data, amount );
    }

    public ItemArmor( int material, short data, int amount, NBTTagCompound nbt ) {
        super( material, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    public abstract float getReductionValue();

    protected boolean isBetter( ItemStack oldItem ) {
        // Armor is better than no armor!
        if ( !( oldItem instanceof ItemArmor ) ) {
            return true;
        }

        return ( (ItemArmor) oldItem ).getReductionValue() < this.getReductionValue();
    }

}
