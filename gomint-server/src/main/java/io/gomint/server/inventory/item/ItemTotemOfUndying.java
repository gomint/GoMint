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
@RegisterInfo( sId = "minecraft:totem_of_undying" )
public class ItemTotemOfUndying extends ItemStack< io.gomint.inventory.item.ItemTotemOfUndying> implements io.gomint.inventory.item.ItemTotemOfUndying {

    @Override
    public ItemType itemType() {
        return ItemType.TOTEM_OF_UNDYING;
    }

}
