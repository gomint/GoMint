package io.gomint.server.inventory.item;

import io.gomint.server.inventory.item.annotation.CanBeDamaged;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
public abstract class ItemArmor<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> {

    public abstract float getReductionValue();

    /**
     * Check if this armor is better than the old one
     *
     * @param oldItem old armor piece
     * @return true if this is better, false if not
     */
    protected boolean isBetter( ItemStack<?> oldItem ) {
        // Armor is better than no armor!
        if ( !( oldItem instanceof ItemArmor ) ) {
            return true;
        }

        return ( (ItemArmor<?>) oldItem ).getReductionValue() < this.getReductionValue();
    }

}
