/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordStal;
import io.gomint.server.inventory.item.ItemRecordStrad;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordStradGenerator implements ItemGenerator {

   @Override
   public ItemRecordStrad generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordStrad( data, amount, nbt );
   }

   @Override
   public ItemRecordStrad generate( short data, byte amount ) {
       return new ItemRecordStrad( data, amount );
   }

}
