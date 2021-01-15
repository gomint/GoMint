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
@RegisterInfo( id = 13 )
public class EnchantmentFireAspect extends Enchantment implements io.gomint.enchant.EnchantmentFireAspect {

    /**
     * Create new enchantment fire aspect
     */
    public EnchantmentFireAspect() {
        super( (short) 2 );
    }

    @Override
    public int getMinEnchantAbility( short level ) {
        return (byte) ( 10 + ( level - 1 ) * 20 );
    }

    @Override
    public int getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 50 );
    }

    @Override
    public boolean canBeApplied(ItemStack<?> itemStack ) {
        return itemStack.itemType() == ItemType.DIAMOND_SWORD ||
            itemStack.itemType() == ItemType.STONE_SWORD ||
            itemStack.itemType() == ItemType.GOLDEN_SWORD ||
            itemStack.itemType() == ItemType.IRON_SWORD ||
            itemStack.itemType() == ItemType.WOODEN_SWORD ||
            itemStack.itemType() == ItemType.NETHERITE_SWORD;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

}
