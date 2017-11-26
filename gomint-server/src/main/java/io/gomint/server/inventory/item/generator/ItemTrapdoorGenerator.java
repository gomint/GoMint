package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTrapdoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTrapdoorGenerator implements ItemGenerator {

   @Override
   public ItemTrapdoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTrapdoor( data, amount, nbt );
   }

   @Override
   public ItemTrapdoor generate( short data, byte amount ) {
       return new ItemTrapdoor( data, amount );
   }

}
