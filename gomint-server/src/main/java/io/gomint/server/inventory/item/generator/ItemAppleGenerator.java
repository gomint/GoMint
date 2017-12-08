package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemApple;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAppleGenerator implements ItemGenerator {

   @Override
   public ItemApple generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemApple( data, amount, nbt );
   }

   @Override
   public ItemApple generate( short data, byte amount ) {
       return new ItemApple( data, amount );
   }

}
