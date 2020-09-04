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
@RegisterInfo( sId = "minecraft:real_double_stone_slab", id = 44 )
@RegisterInfo( sId = "minecraft:real_double_stone_slab2", id = 181 )
@RegisterInfo( sId = "minecraft:real_double_stone_slab3", id = -162 )
@RegisterInfo( sId = "minecraft:real_double_stone_slab4", id = -166 )
@RegisterInfo( sId = "minecraft:polished_blackstone_double_slab", id = -294 )
@RegisterInfo( sId = "minecraft:polished_blackstone_brick_double_slab", id = -285 )
public class ItemDoubleStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleStoneSlab {

    @Override
    public ItemType getItemType() {
        return ItemType.DOUBLE_STONE_SLAB;
    }

    @Override
    public String getBlockId() {
        return super.getBlockId().replace("real_", "");
    }

}
