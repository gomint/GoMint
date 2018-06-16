package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSlimeball;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSlimeballGenerator implements ItemGenerator {

   @Override
   public ItemSlimeball generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSlimeball( data, amount, nbt );
   }

   @Override
   public ItemSlimeball generate( short data, byte amount ) {
       return new ItemSlimeball( data, amount );
   }

}
