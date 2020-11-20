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
@RegisterInfo( sId = "minecraft:black_dye", id = 393 )
public class ItemBlackDye extends ItemStack implements io.gomint.inventory.item.ItemBlackDye {

    @Override
    public ItemType getItemType() {
        return ItemType.BLACK_DYE;
    }

}
