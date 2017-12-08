/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordChirp;
import io.gomint.server.inventory.item.ItemRecordFar;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordFarGenerator implements ItemGenerator {

   @Override
   public ItemRecordFar generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordFar( data, amount, nbt );
   }

   @Override
   public ItemRecordFar generate( short data, byte amount ) {
       return new ItemRecordFar( data, amount );
   }

}
