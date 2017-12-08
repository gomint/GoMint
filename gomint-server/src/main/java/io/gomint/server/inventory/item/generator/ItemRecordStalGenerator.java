/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordMellohi;
import io.gomint.server.inventory.item.ItemRecordStal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordStalGenerator implements ItemGenerator {

   @Override
   public ItemRecordStal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordStal( data, amount, nbt );
   }

   @Override
   public ItemRecordStal generate( short data, byte amount ) {
       return new ItemRecordStal( data, amount );
   }

}
