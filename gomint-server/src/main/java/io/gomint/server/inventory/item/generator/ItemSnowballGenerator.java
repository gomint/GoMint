package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSnowball;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSnowballGenerator implements ItemGenerator {

   @Override
   public ItemSnowball generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSnowball( data, amount, nbt );
   }

   @Override
   public ItemSnowball generate( short data, byte amount ) {
       return new ItemSnowball( data, amount );
   }

}
