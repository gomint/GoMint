package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemClock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemClockGenerator implements ItemGenerator {

   @Override
   public ItemClock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemClock( data, amount, nbt );
   }

   @Override
   public ItemClock generate( short data, byte amount ) {
       return new ItemClock( data, amount );
   }

}
