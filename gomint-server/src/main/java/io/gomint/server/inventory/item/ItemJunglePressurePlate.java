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
@RegisterInfo(sId = "minecraft:jungle_pressure_plate", id = -153)
public class ItemJunglePressurePlate extends ItemStack implements io.gomint.inventory.item.ItemJunglePressurePlate {

    @Override
    public ItemType getItemType() {
        return ItemType.JUNGLE_PRESSURE_PLATE;
    }

}
