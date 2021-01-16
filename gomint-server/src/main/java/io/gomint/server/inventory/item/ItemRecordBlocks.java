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
@RegisterInfo( sId = "minecraft:record_blocks", id = 502 )
public class ItemRecordBlocks extends ItemStack< io.gomint.inventory.item.ItemRecordBlocks> implements io.gomint.inventory.item.ItemRecordBlocks {



    @Override
    public ItemType itemType() {
        return ItemType.RECORD_BLOCKS;
    }

}
