package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenShovel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenShovelGenerator implements ItemGenerator {

   @Override
   public ItemWoodenShovel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenShovel( data, amount, nbt );
   }

   @Override
   public ItemWoodenShovel generate( short data, byte amount ) {
       return new ItemWoodenShovel( data, amount );
   }

}
