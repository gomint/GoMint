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
@RegisterInfo( sId = "minecraft:crimson_pressure_plate", id = -262 )
public class ItemCrimsonPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemCrimsonPressurePlate {

    @Override
    public ItemType getItemType() {
        return ItemType.CRIMSON_PRESSURE_PLATE;
    }

}
