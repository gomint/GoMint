package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTripwire;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTripwireGenerator implements ItemGenerator {

   @Override
   public ItemTripwire generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTripwire( data, amount, nbt );
   }

   @Override
   public ItemTripwire generate( short data, byte amount ) {
       return new ItemTripwire( data, amount );
   }

}
