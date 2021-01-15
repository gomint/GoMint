/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.enchant.Rarity;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
public class EnchantmentSmite extends Enchantment implements io.gomint.enchant.EnchantmentSmite {

    /**
     * Create new enchantment smite
     */
    public EnchantmentSmite() {
        super( (short) 5 );
    }

    @Override
    public int minEnchantAbility( short level ) {
        return (byte) ( 5 + ( level - 1 ) * 8 );
    }

    @Override
    public int maxEnchantAbility( short level ) {
        return (byte) ( minEnchantAbility( level ) + 20 );
    }

    @Override
    public boolean canBeApplied(ItemStack<?> itemStack ) {
        return EnchantmentHelper.canBeAppliedToSwords(itemStack) ||
            EnchantmentHelper.canBeAppliedToAxe(itemStack);
    }

    @Override
    public Rarity rarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean collidesWith(Enchantment enchantment) {
        return enchantment instanceof EnchantmentBaneOfArthopods ||
            enchantment instanceof EnchantmentSharpness ||
            super.collidesWith(enchantment);
    }
}
