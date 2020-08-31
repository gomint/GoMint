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
@RegisterInfo( sId = "minecraft:hard_stained_glass_pane", id = 191 )
public class ItemHardStainedGlassPane extends ItemStack implements io.gomint.inventory.item.ItemHardStainedGlassPane {

    @Override
    public ItemType getItemType() {
        return ItemType.HARD_STAINED_GLASS_PANE;
    }

}
