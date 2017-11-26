package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDispenser;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDispenserGenerator implements ItemGenerator {

   @Override
   public ItemDispenser generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDispenser( data, amount, nbt );
   }

   @Override
   public ItemDispenser generate( short data, byte amount ) {
       return new ItemDispenser( data, amount );
   }

}
