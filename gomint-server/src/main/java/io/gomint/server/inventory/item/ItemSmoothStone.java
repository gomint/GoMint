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
@RegisterInfo(id = -183, sId = "minecraft:smooth_stone")
public class ItemSmoothStone extends ItemStack implements io.gomint.inventory.item.ItemSmoothStone {

    @Override
    public ItemType getItemType() {
        return ItemType.SMOOTH_STONE;
    }

}
