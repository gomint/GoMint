package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndRod;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndRodGenerator implements ItemGenerator {

   @Override
   public ItemEndRod generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndRod( data, amount, nbt );
   }

   @Override
   public ItemEndRod generate( short data, byte amount ) {
       return new ItemEndRod( data, amount );
   }

}
