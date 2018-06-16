/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronNugget;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronNuggetGenerator implements ItemGenerator {

    @Override
    public ItemIronNugget generate( short data, byte amount, NBTTagCompound nbt ) {
        return new ItemIronNugget( data, amount, nbt );
    }

    @Override
    public ItemIronNugget generate( short data, byte amount ) {
        return new ItemIronNugget( data, amount );
    }

}
