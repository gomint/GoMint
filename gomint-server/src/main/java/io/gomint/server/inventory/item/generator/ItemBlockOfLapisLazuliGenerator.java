package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfLapisLazuli;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfLapisLazuliGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfLapisLazuli generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfLapisLazuli( data, amount, nbt );
   }

   @Override
   public ItemBlockOfLapisLazuli generate( short data, byte amount ) {
       return new ItemBlockOfLapisLazuli( data, amount );
   }

}
