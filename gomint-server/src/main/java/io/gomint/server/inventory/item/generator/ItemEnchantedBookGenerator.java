package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEnchantedBook;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEnchantedBookGenerator implements ItemGenerator {

   @Override
   public ItemEnchantedBook generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEnchantedBook( data, amount, nbt );
   }

   @Override
   public ItemEnchantedBook generate( short data, byte amount ) {
       return new ItemEnchantedBook( data, amount );
   }

}
