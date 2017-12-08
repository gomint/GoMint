package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfQuartz;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfQuartzGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfQuartz generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfQuartz( data, amount, nbt );
   }

   @Override
   public ItemBlockOfQuartz generate( short data, byte amount ) {
       return new ItemBlockOfQuartz( data, amount );
   }

}
