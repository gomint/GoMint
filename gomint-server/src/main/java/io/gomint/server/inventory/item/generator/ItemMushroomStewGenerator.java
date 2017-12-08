package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMushroomStew;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMushroomStewGenerator implements ItemGenerator {

   @Override
   public ItemMushroomStew generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMushroomStew( data, amount, nbt );
   }

   @Override
   public ItemMushroomStew generate( short data, byte amount ) {
       return new ItemMushroomStew( data, amount );
   }

}
