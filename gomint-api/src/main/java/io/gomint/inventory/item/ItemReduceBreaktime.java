package io.gomint.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 *
 * This interface is attached to items which modify the breaking time of items (like pickaxe, shovels, shears etc.)
 */
public interface ItemReduceBreaktime extends ItemStack {

    /**
     * Get the divisor of which the item reduces the break times
     *
     * @return divisor for the formula
     */
    float getDivisor();

}
