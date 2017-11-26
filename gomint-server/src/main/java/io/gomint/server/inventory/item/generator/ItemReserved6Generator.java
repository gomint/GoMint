package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemReserved6;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemReserved6Generator implements ItemGenerator {

   @Override
   public ItemReserved6 generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemReserved6( data, amount, nbt );
   }

   @Override
   public ItemReserved6 generate( short data, byte amount ) {
       return new ItemReserved6( data, amount );
   }

}
