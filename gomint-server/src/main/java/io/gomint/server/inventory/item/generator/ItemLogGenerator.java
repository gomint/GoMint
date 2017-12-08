package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLog;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLogGenerator implements ItemGenerator {

   @Override
   public ItemLog generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLog( data, amount, nbt );
   }

   @Override
   public ItemLog generate( short data, byte amount ) {
       return new ItemLog( data, amount );
   }

}
