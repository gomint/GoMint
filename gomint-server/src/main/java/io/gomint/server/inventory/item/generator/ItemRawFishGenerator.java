package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawFish;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawFishGenerator implements ItemGenerator {

   @Override
   public ItemRawFish generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawFish( data, amount, nbt );
   }

   @Override
   public ItemRawFish generate( short data, byte amount ) {
       return new ItemRawFish( data, amount );
   }

}
