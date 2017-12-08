package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMelonStem;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMelonStemGenerator implements ItemGenerator {

   @Override
   public ItemMelonStem generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMelonStem( data, amount, nbt );
   }

   @Override
   public ItemMelonStem generate( short data, byte amount ) {
       return new ItemMelonStem( data, amount );
   }

}
