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
@RegisterInfo( sId = "minecraft:nether_gold_ore", id = -288 )
public class ItemNetherGoldOre extends ItemStack implements io.gomint.inventory.item.ItemNetherGoldOre {

    @Override
    public ItemType getItemType() {
        return ItemType.NETHER_GOLD_ORE;
    }

}
