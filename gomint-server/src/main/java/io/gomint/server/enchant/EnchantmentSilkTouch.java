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
@RegisterInfo( id = 16 )
public class EnchantmentSilkTouch extends Enchantment {

    /**
     * Create new enchantment smite
     *
     * @param level of this enchantment
     */
    public EnchantmentSilkTouch() {
        super( (short) 1 );
    }

    @Override
    public byte getMinEnchantAbility( short level ) {
        return 15;
    }

    @Override
    public byte getMaxEnchantAbility( short level ) {
        return 65;
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
            itemStack.getItemType() == ItemType.SHEARS;
    }

}
