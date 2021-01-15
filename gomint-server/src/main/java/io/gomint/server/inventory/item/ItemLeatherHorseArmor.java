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
@RegisterInfo( sId = "minecraft:leather_horse_armor", id = 520 )
public class ItemLeatherHorseArmor extends ItemStack< io.gomint.inventory.item.ItemLeatherHorseArmor> implements io.gomint.inventory.item.ItemLeatherHorseArmor {

    @Override
    public ItemType itemType() {
        return ItemType.LEATHER_HORSE_ARMOR;
    }

}
