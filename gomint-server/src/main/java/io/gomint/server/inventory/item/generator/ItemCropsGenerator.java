package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCrops;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCropsGenerator implements ItemGenerator {

   @Override
   public ItemCrops generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCrops( data, amount, nbt );
   }

   @Override
   public ItemCrops generate( short data, byte amount ) {
       return new ItemCrops( data, amount );
   }

}
