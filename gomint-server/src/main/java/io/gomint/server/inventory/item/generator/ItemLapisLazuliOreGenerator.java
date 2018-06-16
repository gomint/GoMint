package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLapisLazuliOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLapisLazuliOreGenerator implements ItemGenerator {

   @Override
   public ItemLapisLazuliOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLapisLazuliOre( data, amount, nbt );
   }

   @Override
   public ItemLapisLazuliOre generate( short data, byte amount ) {
       return new ItemLapisLazuliOre( data, amount );
   }

}
