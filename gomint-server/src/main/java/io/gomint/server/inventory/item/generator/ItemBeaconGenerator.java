package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBeacon;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBeaconGenerator implements ItemGenerator {

   @Override
   public ItemBeacon generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBeacon( data, amount, nbt );
   }

   @Override
   public ItemBeacon generate( short data, byte amount ) {
       return new ItemBeacon( data, amount );
   }

}
