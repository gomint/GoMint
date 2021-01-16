/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dark_oak_pressure_plate", id = -152 )
public class ItemDarkOakPressurePlate extends ItemStack<io.gomint.inventory.item.ItemDarkOakPressurePlate> implements io.gomint.inventory.item.ItemDarkOakPressurePlate {

    @Override
    public ItemType itemType() {
        return ItemType.DARK_OAK_PRESSURE_PLATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
