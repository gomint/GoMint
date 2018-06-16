package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCocoa;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCocoaGenerator implements ItemGenerator {

   @Override
   public ItemCocoa generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCocoa( data, amount, nbt );
   }

   @Override
   public ItemCocoa generate( short data, byte amount ) {
       return new ItemCocoa( data, amount );
   }

}
