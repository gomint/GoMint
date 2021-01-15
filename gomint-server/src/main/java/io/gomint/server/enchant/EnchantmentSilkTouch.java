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
@RegisterInfo( id = 16 )
public class EnchantmentSilkTouch extends Enchantment implements io.gomint.enchant.EnchantmentSilkTouch {

    /**
     * Create new enchantment silk touch
     */
    public EnchantmentSilkTouch() {
        super( (short) 1 );
    }

    @Override
    public int getMinEnchantAbility( short level ) {
        return 15;
    }

    @Override
    public int getMaxEnchantAbility( short level ) {
        return 65;
    }

    @Override
    public boolean canBeApplied(ItemStack<?> itemStack ) {
        return EnchantmentHelper.canBeAppliedToTools(itemStack) ||
            itemStack.itemType() == ItemType.SHEARS;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.VERY_RARE;
    }

}
