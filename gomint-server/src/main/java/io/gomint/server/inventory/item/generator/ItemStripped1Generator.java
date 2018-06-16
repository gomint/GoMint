/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStripped1;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStripped1Generator implements ItemGenerator {

    @Override
    public ItemStripped1 generate( short data, byte amount, NBTTagCompound nbt ) {
        return new ItemStripped1( data, amount, nbt );
    }

    @Override
    public ItemStripped1 generate( short data, byte amount ) {
        return new ItemStripped1( data, amount );
    }

}
