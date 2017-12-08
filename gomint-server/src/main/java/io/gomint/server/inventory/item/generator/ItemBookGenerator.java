package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBook;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBookGenerator implements ItemGenerator {

   @Override
   public ItemBook generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBook( data, amount, nbt );
   }

   @Override
   public ItemBook generate( short data, byte amount ) {
       return new ItemBook( data, amount );
   }

}
