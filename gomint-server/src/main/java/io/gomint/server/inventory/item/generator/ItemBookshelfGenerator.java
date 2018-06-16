package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBookshelf;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBookshelfGenerator implements ItemGenerator {

   @Override
   public ItemBookshelf generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBookshelf( data, amount, nbt );
   }

   @Override
   public ItemBookshelf generate( short data, byte amount ) {
       return new ItemBookshelf( data, amount );
   }

}
