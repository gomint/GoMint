/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemLeatherArmor extends ItemColoredArmor {

    @Override
    public int getEnchantAbility() {
        return 15;
    }

}
