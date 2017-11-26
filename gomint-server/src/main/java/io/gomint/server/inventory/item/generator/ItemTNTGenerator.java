package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTNT;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTNTGenerator implements ItemGenerator {

   @Override
   public ItemTNT generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTNT( data, amount, nbt );
   }

   @Override
   public ItemTNT generate( short data, byte amount ) {
       return new ItemTNT( data, amount );
   }

}
