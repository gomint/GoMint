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
@RegisterInfo( id = 20 )
public class EnchantmentPunch extends Enchantment implements io.gomint.enchant.EnchantmentPunch {

    /**
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentPunch( short level ) {
        super( (short) 2, level );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return (byte) ( 12 + ( level - 1 ) * 20 );
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 25 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getType() == ItemType.BOW;
    }

}
