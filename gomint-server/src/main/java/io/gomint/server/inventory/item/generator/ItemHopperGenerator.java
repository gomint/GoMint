package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemHopper;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemHopperGenerator implements ItemGenerator {

   @Override
   public ItemHopper generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemHopper( data, amount, nbt );
   }

   @Override
   public ItemHopper generate( short data, byte amount ) {
       return new ItemHopper( data, amount );
   }

}
