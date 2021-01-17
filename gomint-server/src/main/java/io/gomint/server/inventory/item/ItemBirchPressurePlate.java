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
@RegisterInfo( sId = "minecraft:birch_pressure_plate" )
public class ItemBirchPressurePlate extends ItemStack<io.gomint.inventory.item.ItemBirchPressurePlate> implements io.gomint.inventory.item.ItemBirchPressurePlate {

    @Override
    public ItemType itemType() {
        return ItemType.BIRCH_PRESSURE_PLATE;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
