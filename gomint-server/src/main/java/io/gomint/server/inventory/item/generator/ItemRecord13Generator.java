/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRail;
import io.gomint.server.inventory.item.ItemRecord13;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecord13Generator implements ItemGenerator {

   @Override
   public ItemRecord13 generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecord13( data, amount, nbt );
   }

   @Override
   public ItemRecord13 generate( short data, byte amount ) {
       return new ItemRecord13( data, amount );
   }

}
