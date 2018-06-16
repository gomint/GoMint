package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfIron;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfIronGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfIron generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfIron( data, amount, nbt );
   }

   @Override
   public ItemBlockOfIron generate( short data, byte amount ) {
       return new ItemBlockOfIron( data, amount );
   }

}
