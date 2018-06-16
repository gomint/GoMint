package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrewingStand;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrewingStandGenerator implements ItemGenerator {

   @Override
   public ItemBrewingStand generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrewingStand( data, amount, nbt );
   }

   @Override
   public ItemBrewingStand generate( short data, byte amount ) {
       return new ItemBrewingStand( data, amount );
   }

}
