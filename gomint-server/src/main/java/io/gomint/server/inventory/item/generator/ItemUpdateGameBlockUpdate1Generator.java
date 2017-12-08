package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemUpdateGameBlockUpdate1;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemUpdateGameBlockUpdate1Generator implements ItemGenerator {

   @Override
   public ItemUpdateGameBlockUpdate1 generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemUpdateGameBlockUpdate1( data, amount, nbt );
   }

   @Override
   public ItemUpdateGameBlockUpdate1 generate( short data, byte amount ) {
       return new ItemUpdateGameBlockUpdate1( data, amount );
   }

}
