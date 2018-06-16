package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfRedstone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfRedstoneGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfRedstone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfRedstone( data, amount, nbt );
   }

   @Override
   public ItemBlockOfRedstone generate( short data, byte amount ) {
       return new ItemBlockOfRedstone( data, amount );
   }

}
