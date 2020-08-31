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
@RegisterInfo( sId = "minecraft:coral_fan_hang3", id = -137 )
public class ItemCoralFanHang3 extends ItemStack implements io.gomint.inventory.item.ItemCoralFanHang3 {

    @Override
    public ItemType getItemType() {
        return ItemType.CORAL_FAN_HANG3;
    }

}
