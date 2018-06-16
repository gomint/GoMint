package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStationaryWater;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStationaryWaterGenerator implements ItemGenerator {

   @Override
   public ItemStationaryWater generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStationaryWater( data, amount, nbt );
   }

   @Override
   public ItemStationaryWater generate( short data, byte amount ) {
       return new ItemStationaryWater( data, amount );
   }

}
