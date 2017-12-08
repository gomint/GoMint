package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenHoe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenHoeGenerator implements ItemGenerator {

   @Override
   public ItemWoodenHoe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenHoe( data, amount, nbt );
   }

   @Override
   public ItemWoodenHoe generate( short data, byte amount ) {
       return new ItemWoodenHoe( data, amount );
   }

}
