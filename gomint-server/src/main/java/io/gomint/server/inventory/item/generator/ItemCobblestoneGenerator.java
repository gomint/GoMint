package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCobblestone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCobblestoneGenerator implements ItemGenerator {

   @Override
   public ItemCobblestone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCobblestone( data, amount, nbt );
   }

   @Override
   public ItemCobblestone generate( short data, byte amount ) {
       return new ItemCobblestone( data, amount );
   }

}
