package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLever;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeverGenerator implements ItemGenerator {

   @Override
   public ItemLever generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLever( data, amount, nbt );
   }

   @Override
   public ItemLever generate( short data, byte amount ) {
       return new ItemLever( data, amount );
   }

}
