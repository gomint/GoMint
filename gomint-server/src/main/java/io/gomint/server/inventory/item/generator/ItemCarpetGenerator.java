package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCarpet;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCarpetGenerator implements ItemGenerator {

   @Override
   public ItemCarpet generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCarpet( data, amount, nbt );
   }

   @Override
   public ItemCarpet generate( short data, byte amount ) {
       return new ItemCarpet( data, amount );
   }

}
