/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cooked_mutton" )
public class ItemCookedMutton extends ItemFood<io.gomint.inventory.item.ItemCookedMutton> implements io.gomint.inventory.item.ItemCookedMutton {

    @Override
    public ItemType itemType() {
        return ItemType.COOKED_MUTTON;
    }

    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

}
