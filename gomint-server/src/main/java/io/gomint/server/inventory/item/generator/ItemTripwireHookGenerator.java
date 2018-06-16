package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTripwireHook;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTripwireHookGenerator implements ItemGenerator {

   @Override
   public ItemTripwireHook generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTripwireHook( data, amount, nbt );
   }

   @Override
   public ItemTripwireHook generate( short data, byte amount ) {
       return new ItemTripwireHook( data, amount );
   }

}
