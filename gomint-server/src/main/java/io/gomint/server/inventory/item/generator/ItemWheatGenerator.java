package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWheat;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWheatGenerator implements ItemGenerator {

   @Override
   public ItemWheat generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWheat( data, amount, nbt );
   }

   @Override
   public ItemWheat generate( short data, byte amount ) {
       return new ItemWheat( data, amount );
   }

}
