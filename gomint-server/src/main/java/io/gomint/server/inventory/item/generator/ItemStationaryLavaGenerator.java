package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStationaryLava;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStationaryLavaGenerator implements ItemGenerator {

   @Override
   public ItemStationaryLava generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStationaryLava( data, amount, nbt );
   }

   @Override
   public ItemStationaryLava generate( short data, byte amount ) {
       return new ItemStationaryLava( data, amount );
   }

}
