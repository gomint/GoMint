package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCake;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCakeGenerator implements ItemGenerator {

   @Override
   public ItemCake generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCake( data, amount, nbt );
   }

   @Override
   public ItemCake generate( short data, byte amount ) {
       return new ItemCake( data, amount );
   }

}
