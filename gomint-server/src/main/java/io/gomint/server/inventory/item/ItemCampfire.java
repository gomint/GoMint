/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 720, sId = "minecraft:campfire")
public class ItemCampfire extends ItemStack implements io.gomint.inventory.item.ItemCampfire {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public String getBlockId() {
        return "minecraft:campfire";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CAMPFIRE;
    }
}
