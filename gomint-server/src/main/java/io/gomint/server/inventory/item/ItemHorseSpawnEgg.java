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
@RegisterInfo( sId = "minecraft:horse_spawn_egg" )
public class ItemHorseSpawnEgg extends ItemStack< io.gomint.inventory.item.ItemHorseSpawnEgg> implements io.gomint.inventory.item.ItemHorseSpawnEgg {

    @Override
    public ItemType itemType() {
        return ItemType.HORSE_SPAWN_EGG;
    }

}
