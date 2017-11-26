package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLapisLazuliBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLapisLazuliBlockGenerator implements ItemGenerator {

   @Override
   public ItemLapisLazuliBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLapisLazuliBlock( data, amount, nbt );
   }

   @Override
   public ItemLapisLazuliBlock generate( short data, byte amount ) {
       return new ItemLapisLazuliBlock( data, amount );
   }

}
