/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.server.inventory.item.ItemStack;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Enchantment implements io.gomint.enchant.Enchantment {

    @Getter
    private final short maxLevel;
    private short level;

    /**
     * Create new enchantment
     *
     * @param maxLevel which should be used to cap enchantment
     * @param level    of the enchantment
     */
    Enchantment( short maxLevel, short level ) {
        this.maxLevel = maxLevel;
        this.level = level;
    }

    @Override
    public short getLevel() {
        return this.level;
    }

    /**
     * Get the minimum ability needed to apply this enchantment on the given level
     *
     * @param level of enchantment
     * @return minimum needed enchant ability
     */
    public byte getMinEnchantAbility( short level ) {
        return 0;
    }

    /**
     * Get the maximum ability needed to apply this enchantment on the given level
     *
     * @param level of enchantment
     * @return maximum needed enchant ability
     */
    public byte getMaxEnchantAbility( short level ) {
        return 0;
    }

    /**
     * Check if the item can apply this enchantment
     *
     * @param itemStack which wants this enchantment applied
     * @return true when it can be applied, false otherwise
     */
    public boolean canBeApplied( ItemStack itemStack ) {
        return true;
    }

}
