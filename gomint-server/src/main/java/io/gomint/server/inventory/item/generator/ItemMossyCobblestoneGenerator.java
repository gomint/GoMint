package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMossyCobblestone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMossyCobblestoneGenerator implements ItemGenerator {

   @Override
   public ItemMossyCobblestone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMossyCobblestone( data, amount, nbt );
   }

   @Override
   public ItemMossyCobblestone generate( short data, byte amount ) {
       return new ItemMossyCobblestone( data, amount );
   }

}
