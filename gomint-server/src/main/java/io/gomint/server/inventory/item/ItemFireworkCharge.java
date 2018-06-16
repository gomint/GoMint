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
@RegisterInfo( id = 402 )
public class ItemFireworkCharge extends ItemStack implements io.gomint.inventory.item.ItemFireworkCharge {

    // CHECKSTYLE:OFF
    public ItemFireworkCharge( short data, int amount ) {
        super( 402, data, amount );
    }

    public ItemFireworkCharge( short data, int amount, NBTTagCompound nbt ) {
        super( 402, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FIREWORK_CHARGE;
    }

}
