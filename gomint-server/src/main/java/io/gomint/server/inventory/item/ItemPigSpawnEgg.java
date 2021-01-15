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
@RegisterInfo( sId = "minecraft:pig_spawn_egg", id = 435 )
public class ItemPigSpawnEgg extends ItemStack< io.gomint.inventory.item.ItemPigSpawnEgg> implements io.gomint.inventory.item.ItemPigSpawnEgg {

    @Override
    public ItemType itemType() {
        return ItemType.PIG_SPAWN_EGG;
    }

}
