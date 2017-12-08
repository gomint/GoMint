/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
@RegisterInfo( id = 207 )
public class ItemFrostedIce extends ItemStack implements io.gomint.inventory.item.ItemFrostedIce {

    // CHECKSTYLE:OFF
    public ItemFrostedIce( short data, int amount ) {
        super( 207, data, amount );
    }

    public ItemFrostedIce( short data, int amount, NBTTagCompound nbt ) {
        super( 207, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FROSTED_ICE;
    }

}
