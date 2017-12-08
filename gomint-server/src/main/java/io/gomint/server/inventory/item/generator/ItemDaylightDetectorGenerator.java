package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDaylightDetector;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDaylightDetectorGenerator implements ItemGenerator {

   @Override
   public ItemDaylightDetector generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDaylightDetector( data, amount, nbt );
   }

   @Override
   public ItemDaylightDetector generate( short data, byte amount ) {
       return new ItemDaylightDetector( data, amount );
   }

}
