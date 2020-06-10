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
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral_fan", id = -133 )
public class ItemCoralFan extends ItemStack implements io.gomint.inventory.item.ItemCoralFan {

    @Override
    public ItemType getType() {
        return ItemType.CORAL_FAN;
    }

}
