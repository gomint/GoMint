package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFishingRod;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFishingRodGenerator implements ItemGenerator {

   @Override
   public ItemFishingRod generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFishingRod( data, amount, nbt );
   }

   @Override
   public ItemFishingRod generate( short data, byte amount ) {
       return new ItemFishingRod( data, amount );
   }

}
