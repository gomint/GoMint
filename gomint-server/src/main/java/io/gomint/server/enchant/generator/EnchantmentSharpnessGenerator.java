/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant.generator;

import io.gomint.server.enchant.Enchantment;
import io.gomint.server.enchant.EnchantmentSharpness;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnchantmentSharpnessGenerator implements EnchantmentGenerator {

    @Override
    public Enchantment generate( short level ) {
        return new EnchantmentSharpness( level );
    }

}
