package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlazeRod;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlazeRodGenerator implements ItemGenerator {

   @Override
   public ItemBlazeRod generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlazeRod( data, amount, nbt );
   }

   @Override
   public ItemBlazeRod generate( short data, byte amount ) {
       return new ItemBlazeRod( data, amount );
   }

}
