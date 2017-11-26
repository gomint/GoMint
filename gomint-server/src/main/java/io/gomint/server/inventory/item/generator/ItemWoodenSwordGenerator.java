package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenSword;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenSwordGenerator implements ItemGenerator {

   @Override
   public ItemWoodenSword generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenSword( data, amount, nbt );
   }

   @Override
   public ItemWoodenSword generate( short data, byte amount ) {
       return new ItemWoodenSword( data, amount );
   }

}
