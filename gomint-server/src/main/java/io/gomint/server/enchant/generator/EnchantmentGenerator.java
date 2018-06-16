/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant.generator;

import io.gomint.server.enchant.Enchantment;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EnchantmentGenerator {

    /**
     * Create a new enchantment instance
     *
     * @param level of the enchantment
     * @return new enchantment instance
     */
    Enchantment generate( short level );

}
