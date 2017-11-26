package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronIngot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronIngotGenerator implements ItemGenerator {

   @Override
   public ItemIronIngot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronIngot( data, amount, nbt );
   }

   @Override
   public ItemIronIngot generate( short data, byte amount ) {
       return new ItemIronIngot( data, amount );
   }

}
