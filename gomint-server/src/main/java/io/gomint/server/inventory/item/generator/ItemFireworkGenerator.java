/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFirework;
import io.gomint.server.inventory.item.ItemFireworkCharge;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFireworkGenerator implements ItemGenerator {

    @Override
    public ItemFirework generate( short data, byte amount, NBTTagCompound nbt ) {
        return new ItemFirework( data, amount, nbt );
    }

    @Override
    public ItemFirework generate( short data, byte amount ) {
        return new ItemFirework( data, amount );
    }

}
