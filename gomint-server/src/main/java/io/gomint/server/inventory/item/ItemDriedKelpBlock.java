/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dried_kelp_block", id = -139 )
public class ItemDriedKelpBlock extends ItemStack< io.gomint.inventory.item.ItemDriedKelpBlock> implements io.gomint.inventory.item.ItemDriedKelpBlock {

    @Override
    public ItemType itemType() {
        return ItemType.DRIED_KELP_BLOCK;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(200000);
    }

}
