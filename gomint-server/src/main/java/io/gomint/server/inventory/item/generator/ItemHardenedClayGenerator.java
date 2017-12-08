package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemHardenedClay;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemHardenedClayGenerator implements ItemGenerator {

   @Override
   public ItemHardenedClay generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemHardenedClay( data, amount, nbt );
   }

   @Override
   public ItemHardenedClay generate( short data, byte amount ) {
       return new ItemHardenedClay( data, amount );
   }

}
