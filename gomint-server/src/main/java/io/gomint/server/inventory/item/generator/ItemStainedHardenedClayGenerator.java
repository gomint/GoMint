package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStainedHardenedClay;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStainedHardenedClayGenerator implements ItemGenerator {

   @Override
   public ItemStainedHardenedClay generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStainedHardenedClay( data, amount, nbt );
   }

   @Override
   public ItemStainedHardenedClay generate( short data, byte amount ) {
       return new ItemStainedHardenedClay( data, amount );
   }

}
