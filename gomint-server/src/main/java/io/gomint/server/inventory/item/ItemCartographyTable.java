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
@RegisterInfo(id = -200, sId = "minecraft:cartography_table")
public class ItemCartographyTable extends ItemStack implements io.gomint.inventory.item.ItemCartographyTable {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public String getBlockId() {
        return "minecraft:cartography_table";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CARTOGRAPHY_TABLE;
    }
}
