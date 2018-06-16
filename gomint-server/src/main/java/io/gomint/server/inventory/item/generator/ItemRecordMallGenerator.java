/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordFar;
import io.gomint.server.inventory.item.ItemRecordMall;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordMallGenerator implements ItemGenerator {

   @Override
   public ItemRecordMall generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordMall( data, amount, nbt );
   }

   @Override
   public ItemRecordMall generate( short data, byte amount ) {
       return new ItemRecordMall( data, amount );
   }

}
