package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
public abstract class ItemReduceTierDiamond extends ItemStack implements ItemReduceBreaktime {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public float getDivisor() {
        return 8;
    }

    @Override
    public short getMaxDamage() {
        return 1561;
    }

    @Override
    public int getEnchantAbility() {
        return 10;
    }

}
