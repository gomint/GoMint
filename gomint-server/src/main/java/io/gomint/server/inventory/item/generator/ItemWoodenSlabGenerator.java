package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenSlabGenerator implements ItemGenerator {

   @Override
   public ItemWoodenSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenSlab( data, amount, nbt );
   }

   @Override
   public ItemWoodenSlab generate( short data, byte amount ) {
       return new ItemWoodenSlab( data, amount );
   }

}
