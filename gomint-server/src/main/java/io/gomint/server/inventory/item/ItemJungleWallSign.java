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
@RegisterInfo( sId = "minecraft:jungle_wall_sign" )
public class ItemJungleWallSign extends ItemStack< io.gomint.inventory.item.ItemJungleWallSign> implements io.gomint.inventory.item.ItemJungleWallSign {

    @Override
    public ItemType itemType() {
        return ItemType.JUNGLE_WALL_SIGN;
    }

}
