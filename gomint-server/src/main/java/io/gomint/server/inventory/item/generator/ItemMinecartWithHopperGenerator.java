package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMinecartWithHopper;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMinecartWithHopperGenerator implements ItemGenerator {

   @Override
   public ItemMinecartWithHopper generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMinecartWithHopper( data, amount, nbt );
   }

   @Override
   public ItemMinecartWithHopper generate( short data, byte amount ) {
       return new ItemMinecartWithHopper( data, amount );
   }

}
