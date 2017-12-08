package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPumpkinStem;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPumpkinStemGenerator implements ItemGenerator {

   @Override
   public ItemPumpkinStem generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPumpkinStem( data, amount, nbt );
   }

   @Override
   public ItemPumpkinStem generate( short data, byte amount ) {
       return new ItemPumpkinStem( data, amount );
   }

}
