package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCoal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCoalGenerator implements ItemGenerator {

   @Override
   public ItemCoal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCoal( data, amount, nbt );
   }

   @Override
   public ItemCoal generate( short data, byte amount ) {
       return new ItemCoal( data, amount );
   }

}
