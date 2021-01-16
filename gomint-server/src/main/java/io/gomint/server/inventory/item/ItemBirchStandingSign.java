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
@RegisterInfo( sId = "minecraft:birch_standing_sign", id = -186 )
public class ItemBirchStandingSign extends ItemStack< io.gomint.inventory.item.ItemBirchStandingSign> implements io.gomint.inventory.item.ItemBirchStandingSign {

    @Override
    public ItemType itemType() {
        return ItemType.BIRCH_STANDING_SIGN;
    }

}
