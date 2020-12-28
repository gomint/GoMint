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
@RegisterInfo( id = 12 )
public class EnchantmentKnockback extends Enchantment implements io.gomint.enchant.EnchantmentKnockback {

    /**
     * Create new enchantment smite
     */
    public EnchantmentKnockback() {
        super( (short) 2 );
    }

    @Override
    public int getMinEnchantAbility( short level ) {
        return (byte) ( 5 + ( level - 1 ) * 20 );
    }

    @Override
    public int getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 50 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return EnchantmentHelper.canBeAppliedToSwords(itemStack);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

}
