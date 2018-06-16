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
@RegisterInfo( id = 6 )
public class EnchantmentRespiration extends Enchantment implements io.gomint.enchant.EnchantmentRespiration {

    /**
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentRespiration( short level ) {
        super( (short) 3, level );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return (byte) ( level * 10 );
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 30 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getType() == ItemType.CHAIN_HELMET ||
            itemStack.getType() == ItemType.DIAMOND_HELMET ||
            itemStack.getType() == ItemType.GOLDEN_HELMET ||
            itemStack.getType() == ItemType.IRON_HELMET ||
            itemStack.getType() == ItemType.LEATHER_HELMET;
    }

}
