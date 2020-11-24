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
@RegisterInfo( sId = "minecraft:spruce_trapdoor", id = -149 )
public class ItemSpruceTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemSpruceTrapdoor {

    @Override
    public ItemType getItemType() {
        return ItemType.SPRUCE_TRAPDOOR;
    }

    @Override
    public long getBurnTime() {
        return 15000;
    }

}
