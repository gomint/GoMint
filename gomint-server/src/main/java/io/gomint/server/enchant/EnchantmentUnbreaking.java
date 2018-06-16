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
@RegisterInfo( id = 17 )
public class EnchantmentUnbreaking extends Enchantment implements io.gomint.enchant.EnchantmentUnbreaking {

    /**
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentUnbreaking( short level ) {
        super( (short) 3, level );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return (byte) ( 5 + 8 * ( level - 1 ) );
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return (byte) ( getMinEnchantAbility( level ) + 50 );
    }

    @Override
    public boolean canBeApplied( ItemStack itemStack ) {
        return itemStack.getType() == ItemType.DIAMOND_PICKAXE ||
            itemStack.getType() == ItemType.STONE_PICKAXE ||
            itemStack.getType() == ItemType.GOLDEN_PICKAXE ||
            itemStack.getType() == ItemType.IRON_PICKAXE ||
            itemStack.getType() == ItemType.WOODEN_PICKAXE ||
            itemStack.getType() == ItemType.DIAMOND_AXE ||
            itemStack.getType() == ItemType.STONE_AXE ||
            itemStack.getType() == ItemType.GOLDEN_AXE ||
            itemStack.getType() == ItemType.IRON_AXE ||
            itemStack.getType() == ItemType.WOODEN_AXE ||
            itemStack.getType() == ItemType.DIAMOND_SHOVEL ||
            itemStack.getType() == ItemType.STONE_SHOVEL ||
            itemStack.getType() == ItemType.GOLDEN_SHOVEL ||
            itemStack.getType() == ItemType.IRON_SHOVEL ||
            itemStack.getType() == ItemType.WOODEN_SHOVEL ||
            itemStack.getType() == ItemType.DIAMOND_SWORD ||
            itemStack.getType() == ItemType.STONE_SWORD ||
            itemStack.getType() == ItemType.GOLDEN_SWORD ||
            itemStack.getType() == ItemType.IRON_SWORD ||
            itemStack.getType() == ItemType.WOODEN_SWORD ||
            itemStack.getType() == ItemType.BOW ||
            itemStack.getType() == ItemType.FISHING_ROD;
    }

}
