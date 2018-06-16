package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedFish;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedFishGenerator implements ItemGenerator {

   @Override
   public ItemCookedFish generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedFish( data, amount, nbt );
   }

   @Override
   public ItemCookedFish generate( short data, byte amount ) {
       return new ItemCookedFish( data, amount );
   }

}
