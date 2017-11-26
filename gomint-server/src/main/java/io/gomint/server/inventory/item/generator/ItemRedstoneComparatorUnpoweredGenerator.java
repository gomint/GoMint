package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneComparatorUnpowered;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneComparatorUnpoweredGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneComparatorUnpowered generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneComparatorUnpowered( data, amount, nbt );
   }

   @Override
   public ItemRedstoneComparatorUnpowered generate( short data, byte amount ) {
       return new ItemRedstoneComparatorUnpowered( data, amount );
   }

}
