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
@RegisterInfo( id = 7 )
public class EnchantmentDepthStrider extends Enchantment implements io.gomint.enchant.EnchantmentDepthStrider {

    /**
     * Create new enchantment depth strider
     */
    public EnchantmentDepthStrider() {
        super( (short) 3 );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return (byte) ( level * 10 );
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 15 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getItemType() == ItemType.CHAIN_BOOTS ||
            itemStack.getItemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.getItemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.getItemType() == ItemType.IRON_BOOTS ||
            itemStack.getItemType() == ItemType.LEATHER_BOOTS;
    }

}
