/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
    public EnchantmentUnbreaking() {
        super( (short) 3 );
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
        return itemStack.getItemType() == ItemType.DIAMOND_PICKAXE ||
            itemStack.getItemType() == ItemType.STONE_PICKAXE ||
            itemStack.getItemType() == ItemType.GOLDEN_PICKAXE ||
            itemStack.getItemType() == ItemType.IRON_PICKAXE ||
            itemStack.getItemType() == ItemType.WOODEN_PICKAXE ||
            itemStack.getItemType() == ItemType.DIAMOND_AXE ||
            itemStack.getItemType() == ItemType.STONE_AXE ||
            itemStack.getItemType() == ItemType.GOLDEN_AXE ||
            itemStack.getItemType() == ItemType.IRON_AXE ||
            itemStack.getItemType() == ItemType.WOODEN_AXE ||
            itemStack.getItemType() == ItemType.DIAMOND_SHOVEL ||
            itemStack.getItemType() == ItemType.STONE_SHOVEL ||
            itemStack.getItemType() == ItemType.GOLDEN_SHOVEL ||
            itemStack.getItemType() == ItemType.IRON_SHOVEL ||
            itemStack.getItemType() == ItemType.WOODEN_SHOVEL ||
            itemStack.getItemType() == ItemType.DIAMOND_SWORD ||
            itemStack.getItemType() == ItemType.STONE_SWORD ||
            itemStack.getItemType() == ItemType.GOLDEN_SWORD ||
            itemStack.getItemType() == ItemType.IRON_SWORD ||
            itemStack.getItemType() == ItemType.WOODEN_SWORD ||
            itemStack.getItemType() == ItemType.BOW ||
            itemStack.getItemType() == ItemType.FISHING_ROD;
    }

}
