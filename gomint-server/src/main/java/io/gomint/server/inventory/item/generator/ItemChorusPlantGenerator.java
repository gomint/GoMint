package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChorusPlant;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChorusPlantGenerator implements ItemGenerator {

   @Override
   public ItemChorusPlant generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChorusPlant( data, amount, nbt );
   }

   @Override
   public ItemChorusPlant generate( short data, byte amount ) {
       return new ItemChorusPlant( data, amount );
   }

}
