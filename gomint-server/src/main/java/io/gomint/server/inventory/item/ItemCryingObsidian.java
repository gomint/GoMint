/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
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
@RegisterInfo( sId = "minecraft:crying_obsidian", id = -289 )
public class ItemCryingObsidian extends ItemStack implements io.gomint.inventory.item.ItemCryingObsidian {

    @Override
    public ItemType getType() {
        return ItemType.CRYING_OBSIDIAN;
    }

}
