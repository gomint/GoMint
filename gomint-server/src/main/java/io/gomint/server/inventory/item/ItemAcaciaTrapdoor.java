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
@RegisterInfo( id = -145 )
public class ItemAcaciaTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemAcaciaTrapdoor {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:acacia_trapdoor";
    }

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_TRAPDOOR;
    }

}
