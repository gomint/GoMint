/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.category;

import io.gomint.server.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemConsumable {

    /**
     * Called when a player consumes this item
     *
     * @param player which consumes this item
     */
    void onConsume( EntityPlayer player );

}
