package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemInvertedDaylightSensor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemInvertedDaylightSensorGenerator implements ItemGenerator {

   @Override
   public ItemInvertedDaylightSensor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemInvertedDaylightSensor( data, amount, nbt );
   }

   @Override
   public ItemInvertedDaylightSensor generate( short data, byte amount ) {
       return new ItemInvertedDaylightSensor( data, amount );
   }

}
