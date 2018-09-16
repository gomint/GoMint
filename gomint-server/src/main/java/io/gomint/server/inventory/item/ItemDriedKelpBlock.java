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
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = -139 )
public class ItemDriedKelpBlock extends ItemStack implements io.gomint.inventory.item.ItemDriedKelpBlock {

    @Override
    public String getBlockId() {
        return "minecraft:dried_kelp_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.DRIED_KELP_BLOCK;
    }

}
