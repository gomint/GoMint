/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecord11;
import io.gomint.server.inventory.item.ItemRecordWait;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordWaitGenerator implements ItemGenerator {

   @Override
   public ItemRecordWait generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordWait( data, amount, nbt );
   }

   @Override
   public ItemRecordWait generate( short data, byte amount ) {
       return new ItemRecordWait( data, amount );
   }

}
