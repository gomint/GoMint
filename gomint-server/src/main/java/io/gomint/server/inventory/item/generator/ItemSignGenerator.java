package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSign;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSignGenerator implements ItemGenerator {

   @Override
   public ItemSign generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSign( data, amount, nbt );
   }

   @Override
   public ItemSign generate( short data, byte amount ) {
       return new ItemSign( data, amount );
   }

}
