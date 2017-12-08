/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordStrad;
import io.gomint.server.inventory.item.ItemRecordWard;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordWardGenerator implements ItemGenerator {

   @Override
   public ItemRecordWard generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordWard( data, amount, nbt );
   }

   @Override
   public ItemRecordWard generate( short data, byte amount ) {
       return new ItemRecordWard( data, amount );
   }

}
