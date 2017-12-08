/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRecordBlocks;
import io.gomint.server.inventory.item.ItemRecordCat;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRecordBlocksGenerator implements ItemGenerator {

   @Override
   public ItemRecordBlocks generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRecordBlocks( data, amount, nbt );
   }

   @Override
   public ItemRecordBlocks generate( short data, byte amount ) {
       return new ItemRecordBlocks( data, amount );
   }

}
