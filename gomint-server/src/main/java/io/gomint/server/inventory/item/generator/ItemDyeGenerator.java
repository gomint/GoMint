package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDye;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDyeGenerator implements ItemGenerator {

   @Override
   public ItemDye generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDye( data, amount, nbt );
   }

   @Override
   public ItemDye generate( short data, byte amount ) {
       return new ItemDye( data, amount );
   }

}
