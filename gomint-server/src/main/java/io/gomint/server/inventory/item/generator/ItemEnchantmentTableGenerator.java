package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEnchantmentTable;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEnchantmentTableGenerator implements ItemGenerator {

   @Override
   public ItemEnchantmentTable generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEnchantmentTable( data, amount, nbt );
   }

   @Override
   public ItemEnchantmentTable generate( short data, byte amount ) {
       return new ItemEnchantmentTable( data, amount );
   }

}
