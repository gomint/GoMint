/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

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
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentFeatherfalling( short level ) {
        super( (short) 4, level );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return (byte) ( 5 + ( level - 1 ) * 6 );
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 10 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getType() == ItemType.CHAIN_BOOTS ||
            itemStack.getType() == ItemType.DIAMOND_BOOTS ||
            itemStack.getType() == ItemType.GOLDEN_BOOTS ||
            itemStack.getType() == ItemType.IRON_BOOTS ||
            itemStack.getType() == ItemType.LEATHER_BOOTS;
    }

}
