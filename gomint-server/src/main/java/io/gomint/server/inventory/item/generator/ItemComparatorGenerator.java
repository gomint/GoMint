package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemComparator;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemComparatorGenerator implements ItemGenerator {

   @Override
   public ItemComparator generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemComparator( data, amount, nbt );
   }

   @Override
   public ItemComparator generate( short data, byte amount ) {
       return new ItemComparator( data, amount );
   }

}
