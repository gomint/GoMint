package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlint;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlintGenerator implements ItemGenerator {

   @Override
   public ItemFlint generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlint( data, amount, nbt );
   }

   @Override
   public ItemFlint generate( short data, byte amount ) {
       return new ItemFlint( data, amount );
   }

}
