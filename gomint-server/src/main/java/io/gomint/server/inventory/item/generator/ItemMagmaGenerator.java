/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMagma;
import io.gomint.server.inventory.item.ItemMagmaCream;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMagmaGenerator implements ItemGenerator {

   @Override
   public ItemMagma generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMagma( data, amount, nbt );
   }

   @Override
   public ItemMagma generate( short data, byte amount ) {
       return new ItemMagma( data, amount );
   }

}
