package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherWart;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherWartGenerator implements ItemGenerator {

   @Override
   public ItemNetherWart generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherWart( data, amount, nbt );
   }

   @Override
   public ItemNetherWart generate( short data, byte amount ) {
       return new ItemNetherWart( data, amount );
   }

}
