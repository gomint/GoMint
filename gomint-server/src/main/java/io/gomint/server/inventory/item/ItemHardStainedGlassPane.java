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
@RegisterInfo( sId = "minecraft:hard_stained_glass_pane", id = 191 )
public class ItemHardStainedGlassPane extends ItemStack< io.gomint.inventory.item.ItemHardStainedGlassPane> implements io.gomint.inventory.item.ItemHardStainedGlassPane {

    @Override
    public ItemType itemType() {
        return ItemType.HARD_STAINED_GLASS_PANE;
    }

}
