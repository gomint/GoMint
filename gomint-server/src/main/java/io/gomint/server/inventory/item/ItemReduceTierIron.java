package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
public abstract class ItemReduceTierIron<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> implements ItemReduceBreaktime<I> {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public float divisor() {
        return 6;
    }

    @Override
    public short maxDamage() {
        return 250;
    }

    @Override
    public int enchantAbility() {
        return 14;
    }

}
