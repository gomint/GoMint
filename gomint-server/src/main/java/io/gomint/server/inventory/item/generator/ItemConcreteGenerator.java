package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemConcrete;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemConcreteGenerator implements ItemGenerator {

   @Override
   public ItemConcrete generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemConcrete( data, amount, nbt );
   }

   @Override
   public ItemConcrete generate( short data, byte amount ) {
       return new ItemConcrete( data, amount );
   }

}
