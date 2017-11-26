package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenDoubleSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenDoubleSlabGenerator implements ItemGenerator {

   @Override
   public ItemWoodenDoubleSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenDoubleSlab( data, amount, nbt );
   }

   @Override
   public ItemWoodenDoubleSlab generate( short data, byte amount ) {
       return new ItemWoodenDoubleSlab( data, amount );
   }

}
