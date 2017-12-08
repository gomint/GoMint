/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemShulkerBox;
import io.gomint.server.inventory.item.ItemShulkerShell;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemShulkerBoxGenerator implements ItemGenerator {

   @Override
   public ItemShulkerBox generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemShulkerBox( data, amount, nbt );
   }

   @Override
   public ItemShulkerBox generate( short data, byte amount ) {
       return new ItemShulkerBox( data, amount );
   }

}
