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
@RegisterInfo( sId = "minecraft:record_chirp", id = 503 )
public class ItemRecordChirp extends ItemStack< io.gomint.inventory.item.ItemRecordChirp> implements io.gomint.inventory.item.ItemRecordChirp {



    @Override
    public ItemType itemType() {
        return ItemType.RECORD_CHIRP;
    }

}
