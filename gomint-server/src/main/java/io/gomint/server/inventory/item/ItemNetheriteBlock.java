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
@RegisterInfo( sId = "minecraft:netherite_block", id = -270 )
public class ItemNetheriteBlock extends ItemStack< io.gomint.inventory.item.ItemNetheriteBlock> implements io.gomint.inventory.item.ItemNetheriteBlock {

    @Override
    public ItemType itemType() {
        return ItemType.NETHERITE_BLOCK;
    }

}
