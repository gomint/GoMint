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
@RegisterInfo( id = 22 )
public class EnchantmentInfinity extends Enchantment implements io.gomint.enchant.EnchantmentInfinity{

    /**
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentInfinity( short level ) {
        super( (short) 1, level );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return 20;
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return 50;
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getType() == ItemType.BOW;
    }

}
