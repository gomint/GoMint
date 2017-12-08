package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFeather;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFeatherGenerator implements ItemGenerator {

   @Override
   public ItemFeather generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFeather( data, amount, nbt );
   }

   @Override
   public ItemFeather generate( short data, byte amount ) {
       return new ItemFeather( data, amount );
   }

}
