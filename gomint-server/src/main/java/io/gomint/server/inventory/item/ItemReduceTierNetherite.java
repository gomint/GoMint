package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;

/**
 * @author KingAli
 * @version 1.0
 */
@CanBeDamaged
public abstract class ItemReduceTierNetherite<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> implements ItemReduceBreaktime<I> {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public float divisor() {
        return 9;
    }

    @Override
    public short maxDamage() {
        return 2031;
    }

    @Override
    public int enchantAbility() {
        return 15;
    }

}
