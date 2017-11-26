package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldIngot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldIngotGenerator implements ItemGenerator {

   @Override
   public ItemGoldIngot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldIngot( data, amount, nbt );
   }

   @Override
   public ItemGoldIngot generate( short data, byte amount ) {
       return new ItemGoldIngot( data, amount );
   }

}
