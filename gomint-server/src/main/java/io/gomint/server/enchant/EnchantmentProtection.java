/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.enchant.Rarity;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 0)
public class EnchantmentProtection extends Enchantment implements io.gomint.enchant.EnchantmentProtection {

    /**
     * Create new enchantment protection
     */
    public EnchantmentProtection() {
        super((short) 4);
    }

    @Override
    public int getMinEnchantAbility(short level) {
        return (byte) (1 + (level - 1) * 11);
    }

    @Override
    public int getMaxEnchantAbility(short level) {
        return (byte) (getMinEnchantAbility(level) + 20);
    }

    @Override
    public boolean canBeApplied(ItemStack itemStack) {
        return EnchantmentHelper.canBeAppliedArmor(itemStack);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public boolean collidesWith(Enchantment enchantment) {
        return enchantment instanceof EnchantmentProjectileProtection ||
            enchantment instanceof EnchantmentFireProtection ||
            enchantment instanceof EnchantmentBlastProtection ||
            super.collidesWith(enchantment);
    }

}
