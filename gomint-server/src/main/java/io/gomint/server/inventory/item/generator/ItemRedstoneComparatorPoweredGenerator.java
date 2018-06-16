package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneComparatorPowered;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneComparatorPoweredGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneComparatorPowered generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneComparatorPowered( data, amount, nbt );
   }

   @Override
   public ItemRedstoneComparatorPowered generate( short data, byte amount ) {
       return new ItemRedstoneComparatorPowered( data, amount );
   }

}
