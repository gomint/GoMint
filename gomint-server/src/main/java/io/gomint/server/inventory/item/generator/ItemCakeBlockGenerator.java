package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCakeBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCakeBlockGenerator implements ItemGenerator {

   @Override
   public ItemCakeBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCakeBlock( data, amount, nbt );
   }

   @Override
   public ItemCakeBlock generate( short data, byte amount ) {
       return new ItemCakeBlock( data, amount );
   }

}
