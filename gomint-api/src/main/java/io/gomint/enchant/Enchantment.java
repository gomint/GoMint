/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.enchant;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Enchantment {

    /**
     * Get the level of this enchantment
     *
     * @return level of enchantment
     */
    short level();

    /**
     * Get the rarity of this enchantment
     *
     * @return rarity of enchantment
     */
    Rarity rarity();

}
