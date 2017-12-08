package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneComparator;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneComparatorGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneComparator generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneComparator( data, amount, nbt );
   }

   @Override
   public ItemRedstoneComparator generate( short data, byte amount ) {
       return new ItemRedstoneComparator( data, amount );
   }

}
