package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrewingStandBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrewingStandBlockGenerator implements ItemGenerator {

   @Override
   public ItemBrewingStandBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrewingStandBlock( data, amount, nbt );
   }

   @Override
   public ItemBrewingStandBlock generate( short data, byte amount ) {
       return new ItemBrewingStandBlock( data, amount );
   }

}
