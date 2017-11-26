package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedRabbit;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedRabbitGenerator implements ItemGenerator {

   @Override
   public ItemCookedRabbit generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedRabbit( data, amount, nbt );
   }

   @Override
   public ItemCookedRabbit generate( short data, byte amount ) {
       return new ItemCookedRabbit( data, amount );
   }

}
