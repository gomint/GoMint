/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:nether_wart_block", id = 214)
public class ItemNetherWartBlock extends ItemStack implements io.gomint.inventory.item.ItemNetherWartBlock {

    @Override
    public ItemType getItemType() {
        return ItemType.NETHER_WART_BLOCK;
    }

}
