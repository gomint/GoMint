package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemUpdateGameBlockUpdate2;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemUpdateGameBlockUpdate2Generator implements ItemGenerator {

   @Override
   public ItemUpdateGameBlockUpdate2 generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemUpdateGameBlockUpdate2( data, amount, nbt );
   }

   @Override
   public ItemUpdateGameBlockUpdate2 generate( short data, byte amount ) {
       return new ItemUpdateGameBlockUpdate2( data, amount );
   }

}
