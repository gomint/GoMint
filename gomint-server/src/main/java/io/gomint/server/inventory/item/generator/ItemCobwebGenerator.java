package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCobweb;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCobwebGenerator implements ItemGenerator {

   @Override
   public ItemCobweb generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCobweb( data, amount, nbt );
   }

   @Override
   public ItemCobweb generate( short data, byte amount ) {
       return new ItemCobweb( data, amount );
   }

}
