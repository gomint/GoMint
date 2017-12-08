package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedSalmon;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedSalmonGenerator implements ItemGenerator {

   @Override
   public ItemCookedSalmon generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedSalmon( data, amount, nbt );
   }

   @Override
   public ItemCookedSalmon generate( short data, byte amount ) {
       return new ItemCookedSalmon( data, amount );
   }

}
