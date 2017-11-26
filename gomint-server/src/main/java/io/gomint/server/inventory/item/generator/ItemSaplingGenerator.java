package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSapling;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSaplingGenerator implements ItemGenerator {

   @Override
   public ItemSapling generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSapling( data, amount, nbt );
   }

   @Override
   public ItemSapling generate( short data, byte amount ) {
       return new ItemSapling( data, amount );
   }

}
