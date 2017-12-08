/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChainCommandBlock;
import io.gomint.server.inventory.item.ItemRepeatingCommandBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChainCommandBlockGenerator implements ItemGenerator {

    @Override
    public ItemChainCommandBlock generate( short data, byte amount, NBTTagCompound nbt ) {
        return new ItemChainCommandBlock( data, amount, nbt );
    }

    @Override
    public ItemChainCommandBlock generate( short data, byte amount ) {
        return new ItemChainCommandBlock( data, amount );
    }

}
