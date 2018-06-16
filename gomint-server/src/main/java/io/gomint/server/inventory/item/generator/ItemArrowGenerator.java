package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemArrow;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemArrowGenerator implements ItemGenerator {

   @Override
   public ItemArrow generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemArrow( data, amount, nbt );
   }

   @Override
   public ItemArrow generate( short data, byte amount ) {
       return new ItemArrow( data, amount );
   }

}
