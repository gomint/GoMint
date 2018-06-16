package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWrittenBook;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWrittenBookGenerator implements ItemGenerator {

   @Override
   public ItemWrittenBook generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWrittenBook( data, amount, nbt );
   }

   @Override
   public ItemWrittenBook generate( short data, byte amount ) {
       return new ItemWrittenBook( data, amount );
   }

}
