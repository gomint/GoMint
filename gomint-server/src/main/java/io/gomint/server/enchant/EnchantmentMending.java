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
@RegisterInfo( id = 26 )
public class EnchantmentMending extends Enchantment implements io.gomint.enchant.EnchantmentMending {

    /**
     * Create new enchantment mending
     */
    public EnchantmentMending() {
        super( (short) 1 );
    }

    @Override
    public int getMinEnchantAbility( short level ) {
        return (byte) ( level * 25 );
    }

    @Override
    public int getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 50 );
    }

    @Override
    public boolean canBeApplied(ItemStack<?> itemStack ) {
        return itemStack.canBeDamaged();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

}
