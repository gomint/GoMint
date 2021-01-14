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
@RegisterInfo( id = 2 )
public class EnchantmentFeatherfalling extends Enchantment implements io.gomint.enchant.EnchantmentFeatherfalling {

    /**
     * Create new enchantment feather falling
     */
    public EnchantmentFeatherfalling() {
        super( (short) 4 );
    }

    @Override
    public int minEnchantAbility( short level ) {
        return (byte) ( 5 + ( level - 1 ) * 6 );
    }

    @Override
    public int maxEnchantAbility( short level ) {
        return (byte) ( minEnchantAbility( level ) + 10 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getItemType() == ItemType.CHAIN_BOOTS ||
            itemStack.getItemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.getItemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.getItemType() == ItemType.IRON_BOOTS ||
            itemStack.getItemType() == ItemType.LEATHER_BOOTS ||
            itemStack.getItemType() == ItemType.NETHERITE_BOOTS;
    }

    @Override
    public Rarity rarity() {
        return Rarity.UNCOMMON;
    }

}
