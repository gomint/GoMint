package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfCoal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfCoalGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfCoal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfCoal( data, amount, nbt );
   }

   @Override
   public ItemBlockOfCoal generate( short data, byte amount ) {
       return new ItemBlockOfCoal( data, amount );
   }

}
