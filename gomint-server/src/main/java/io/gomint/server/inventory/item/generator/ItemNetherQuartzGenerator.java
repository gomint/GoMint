package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherQuartz;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherQuartzGenerator implements ItemGenerator {

   @Override
   public ItemNetherQuartz generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherQuartz( data, amount, nbt );
   }

   @Override
   public ItemNetherQuartz generate( short data, byte amount ) {
       return new ItemNetherQuartz( data, amount );
   }

}
