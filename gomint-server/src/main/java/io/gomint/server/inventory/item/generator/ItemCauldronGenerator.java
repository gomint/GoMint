package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCauldron;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCauldronGenerator implements ItemGenerator {

   @Override
   public ItemCauldron generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCauldron( data, amount, nbt );
   }

   @Override
   public ItemCauldron generate( short data, byte amount ) {
       return new ItemCauldron( data, amount );
   }

}
