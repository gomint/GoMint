package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneGenerator implements ItemGenerator {

   @Override
   public ItemRedstone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstone( data, amount, nbt );
   }

   @Override
   public ItemRedstone generate( short data, byte amount ) {
       return new ItemRedstone( data, amount );
   }

}
