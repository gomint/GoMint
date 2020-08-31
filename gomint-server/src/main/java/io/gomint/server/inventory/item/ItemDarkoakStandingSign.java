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
@RegisterInfo( sId = "minecraft:darkoak_standing_sign", id = -192 )
public class ItemDarkoakStandingSign extends ItemStack implements io.gomint.inventory.item.ItemDarkoakStandingSign {

    @Override
    public ItemType getItemType() {
        return ItemType.DARKOAK_STANDING_SIGN;
    }

}
