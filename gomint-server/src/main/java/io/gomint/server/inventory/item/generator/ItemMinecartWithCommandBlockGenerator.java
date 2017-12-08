/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMinecartWithCommandBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMinecartWithCommandBlockGenerator implements ItemGenerator {

    @Override
    public ItemMinecartWithCommandBlock generate( short data, byte amount, NBTTagCompound nbt ) {
        return new ItemMinecartWithCommandBlock( data, amount, nbt );
    }

    @Override
    public ItemMinecartWithCommandBlock generate( short data, byte amount ) {
        return new ItemMinecartWithCommandBlock( data, amount );
    }

}
