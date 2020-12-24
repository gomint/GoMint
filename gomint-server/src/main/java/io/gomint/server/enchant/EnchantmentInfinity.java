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
@RegisterInfo( id = 22 )
public class EnchantmentInfinity extends Enchantment implements io.gomint.enchant.EnchantmentInfinity{

    /**
     * Create new enchantment infinity
     */
    public EnchantmentInfinity() {
        super( (short) 1 );
    }

    @Override
    public int getMinEnchantAbility( short level ) {
        return 20;
    }

    @Override
    public int getMaxEnchantAbility( short level ) {
        return 50;
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getItemType() == ItemType.BOW;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.VERY_RARE;
    }

}
