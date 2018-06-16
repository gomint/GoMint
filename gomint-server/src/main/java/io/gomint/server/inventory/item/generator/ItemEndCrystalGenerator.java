package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndCrystal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndCrystalGenerator implements ItemGenerator {

   @Override
   public ItemEndCrystal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndCrystal( data, amount, nbt );
   }

   @Override
   public ItemEndCrystal generate( short data, byte amount ) {
       return new ItemEndCrystal( data, amount );
   }

}
