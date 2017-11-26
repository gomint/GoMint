package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCompass;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCompassGenerator implements ItemGenerator {

   @Override
   public ItemCompass generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCompass( data, amount, nbt );
   }

   @Override
   public ItemCompass generate( short data, byte amount ) {
       return new ItemCompass( data, amount );
   }

}
