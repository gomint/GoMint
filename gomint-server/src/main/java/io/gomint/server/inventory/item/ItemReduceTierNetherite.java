package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;

/**
 * @author KingAli
 * @version 1.0
 */
@CanBeDamaged
public abstract class ItemReduceTierNetherite extends ItemStack implements ItemReduceBreaktime {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public float getDivisor() {
        return 9;
    }

    @Override
    public short getMaxDamage() {
        return 2031;
    }

    @Override
    public int getEnchantAbility() {
        return 15;
    }

}
