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
@RegisterInfo( sId = "minecraft:record_far", id = 504 )
public class ItemRecordFar extends ItemStack< io.gomint.inventory.item.ItemRecordFar> implements io.gomint.inventory.item.ItemRecordFar {



    @Override
    public ItemType itemType() {
        return ItemType.RECORD_FAR;
    }

}
