package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;

/**
 * @author KingAli
 * @version 1.0
 */
public abstract class ItemNetheriteArmor<I extends ItemStack<I>> extends ItemArmor<I> {

    @Override
    public int enchantAbility() {
        return 15;
    }

}
