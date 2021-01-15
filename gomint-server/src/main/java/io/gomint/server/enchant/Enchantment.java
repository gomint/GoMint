/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.enchant.Rarity;
import io.gomint.math.MathUtils;
import io.gomint.server.inventory.item.ItemStack;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Enchantment implements io.gomint.enchant.Enchantment {

    private final short maxLevel;
    private short level;

    /**
     * Create new enchantment
     *
     * @param maxLevel which should be used to cap enchantment
     */
    Enchantment(short maxLevel) {
        this.maxLevel = maxLevel;
    }

    Enchantment changeLevel(short level) {
        this.level = MathUtils.clamp(level, (short) 0, this.maxLevel);
        return this;
    }

    public short maxLevel() {
        return maxLevel;
    }

    @Override
    public short level() {
        return this.level;
    }

    /**
     * Get the minimum ability needed to apply this enchantment on the given level
     *
     * @param level of enchantment
     * @return minimum needed enchant ability
     */
    public int minEnchantAbility( short level ) {
        return 1 + level * 10;
    }

    /**
     * Get the maximum ability needed to apply this enchantment on the given level
     *
     * @param level of enchantment
     * @return maximum needed enchant ability
     */
    public int maxEnchantAbility( short level ) {
        return minEnchantAbility( level ) + 5;
    }

    /**
     * Check if the item can apply this enchantment
     *
     * @param itemStack which wants this enchantment applied
     * @return true when it can be applied, false otherwise
     */
    public boolean canBeApplied(ItemStack<?> itemStack ) {
        return true;
    }

    public int minLevel() {
        return 1;
    }

    public abstract Rarity rarity();

    public boolean collidesWith(Enchantment enchantment) {
        return this.getClass() == enchantment.getClass();
    }

}
