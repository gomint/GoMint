package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemClay;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemClayGenerator implements ItemGenerator {

   @Override
   public ItemClay generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemClay( data, amount, nbt );
   }

   @Override
   public ItemClay generate( short data, byte amount ) {
       return new ItemClay( data, amount );
   }

}
